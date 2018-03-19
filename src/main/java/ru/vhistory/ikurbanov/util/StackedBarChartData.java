package ru.vhistory.ikurbanov.util;

import lombok.Data;
import lombok.Getter;
import ru.vhistory.ikurbanov.dto.actions.HistoryAction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StackedBarChartData {
    @Getter
    private String[] categories;
    @Getter
    private List<Series> series = new ArrayList<>();

    public StackedBarChartData(String[] users, String[] actions, ArrayList<HistoryAction> historyActions){
        try{
        this.categories = actions;
        Map<String, List<HistoryAction>> usersToActions= historyActions.stream().collect(Collectors.groupingBy(HistoryAction::getUser));

        for (String user : users) {
            if (usersToActions.containsKey(user)){
                List<Integer> data = new ArrayList<>();
                for (String action : actions) {
                    data.add((int)usersToActions.get(user)
                            .stream()
                            .filter(x->x.getName().equals(action))
                            .filter(distinctByKey(x->roundDate(x.getTime())))
                            .count());
                }
                series.add(new Series(user,data));
            } else {
                List<Integer> data = new ArrayList<>();
                for (String action : actions) {
                    data.add(0);
                }
                series.add(new Series(user,data));
            }

        }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Data
    public class Series{
        private String name;
        private List<Integer> data;

        Series(String name, List<Integer> data) {
            this.name=name;
            this.data = data;
        }
    }

    private static Date roundDate(Date date){
        return new Date(date.getTime()- (date.getTime()%5000));
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
