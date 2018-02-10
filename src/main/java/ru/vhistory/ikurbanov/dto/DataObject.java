package ru.vhistory.ikurbanov.dto;

import lombok.Data;
import lombok.Getter;
import ru.vhistory.ikurbanov.constant.DataObjectType;
import ru.vhistory.ikurbanov.dto.actions.HistoryAction;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DataObject {
    private final String name;
    private final DataObjectType type;
    private List<HistoryAction> historyActions;

    public DataObject(String name, DataObjectType type, List<HistoryAction> historyActions) {
        this.name = name;
        this.type = type;
        this.historyActions = historyActions;
    }


    public void addHistoryAction(HistoryAction historyAction){
        this.historyActions.add(historyAction);
    }

    @Override
    public String toString() {
        return "DataObject{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", historyActions=" + historyActions.stream().map(x -> x.toString()).collect(Collectors.joining(",")) +
                '}';
    }
}
