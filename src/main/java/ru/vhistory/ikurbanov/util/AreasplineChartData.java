package ru.vhistory.ikurbanov.util;

import lombok.Data;
import ru.vhistory.ikurbanov.dto.actions.HistoryAction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Data
public class AreasplineChartData {
    private List<String> categories = new ArrayList<String>();
    private List<Series> series;


    public AreasplineChartData(ArrayList<String> users, ArrayList<String> actions, ArrayList<HistoryAction> historyActions, String generalizePeriod, Date periodBegin, Date periodEnd) throws ParseException{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate startPeriod = LocalDate.parse(simpleDateFormat.format(periodBegin));
        LocalDate endPeriod = LocalDate.parse(simpleDateFormat.format(periodEnd));
        List<LocalDate> localDates = new ArrayList<>();
        while (startPeriod.isBefore(endPeriod) ) {
            localDates.add(startPeriod);
            startPeriod = startPeriod.plusDays(1);
        }


            List<String> allDatesInPeriod = localDates.stream().map((LocalDate x) -> {
                try {
                    return formatDate(simpleDateFormat.parse(x.toString()), generalizePeriod);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return "";
                }
            }).distinct().collect(Collectors.toList());

        Map<String, List<HistoryAction>> result = historyActions.stream()
                .filter(x-> periodBegin.before(x.getTime()) && periodEnd.after(x.getTime()))
                .filter(x-> actions.contains(x.getName()))
                .filter(x-> users.contains(x.getUser()))
//                .collect(Collectors.groupingBy(historyAction -> LocalDate.parse(simpleDateFormat.format(historyAction.getTime()))));
        .collect(Collectors.groupingBy(historyAction -> formatDate(historyAction.getTime(), generalizePeriod)));


        Map<String,List<Integer>> mapSeries = new HashMap<String, List<Integer>>();
        users.forEach(x-> mapSeries.put(x,new ArrayList<Integer>()));


        for (String singleDate : allDatesInPeriod){
            categories.add(singleDate);
            if (result.containsKey(singleDate)){
                Map<String,List<HistoryAction>> usersToActionsInGenPeriod;
                usersToActionsInGenPeriod = result.get(singleDate).stream().collect(Collectors.groupingBy(HistoryAction::getUser));
                users.forEach(x->{
                    if(!usersToActionsInGenPeriod.containsKey(x)){
                        usersToActionsInGenPeriod.put(x,new ArrayList<HistoryAction>());
                    }
                });
                for (Map.Entry<String, List<HistoryAction>> userToActionPair : usersToActionsInGenPeriod.entrySet()) {
                    mapSeries.get(userToActionPair.getKey()).add(((int) userToActionPair.getValue().stream()
                            .filter(distinctByKey(x -> roundDate(x.getTime())))
                            .count()));
                }
            } else {
                users.forEach(x->{
                    mapSeries.get(x).add(0);
                });
            }
        }

//        for(LocalDate ld : localDates){
//            categories.add(ld.toString());
//            if(result.containsKey(ld)){
//                Map<String,List<HistoryAction>> usersToActionsInDay;
//                usersToActionsInDay = result.get(ld).stream().collect(Collectors.groupingBy(HistoryAction::getUser));
//                users.forEach(x->{
//                    if(!usersToActionsInDay.containsKey(x)){
//                        usersToActionsInDay.put(x,new ArrayList<HistoryAction>());
//                    }
//                });
//                for (Map.Entry<String, List<HistoryAction>> userToActionPair : usersToActionsInDay.entrySet()) {
//                    mapSeries.get(userToActionPair.getKey()).add(((int) userToActionPair.getValue().stream().filter(distinctByKey(x -> roundDate(x.getTime()))).count()));
//                }
//            } else {
//                users.forEach(x->{
//                    mapSeries.get(x).add(0);
//                });
//            }
//        }


        List<Series> series= new ArrayList<>();
        for (Map.Entry<String,List<Integer>> userToNumbers : mapSeries.entrySet()){
            series.add(new Series(userToNumbers.getKey(), userToNumbers.getValue()));
        }
        this.series = series;
    }

    private String formatDate(Date date, String generalizePeriod){
        String formattedDate ="";
        switch (generalizePeriod){
            case "day": {
                SimpleDateFormat sdt = new SimpleDateFormat("dd-MM-yyyy");
                formattedDate = sdt.format(date);
                break;
            }
            case "week": {
                SimpleDateFormat sdt = new SimpleDateFormat("W/MM-yyyy");
                formattedDate = sdt.format(date);
                break;
            }
            case "month": {
                SimpleDateFormat sdt = new SimpleDateFormat("MM/yyyy");
                formattedDate = sdt.format(date);
                break;
            }
        }
        return formattedDate;
    }


    private Date roundDate(Date date){
        //TODO: Округление до 5 секунд
        return new Date(date.getTime()- (date.getTime()%5000));
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
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
}
