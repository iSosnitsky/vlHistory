package ru.vhistory.ikurbanov.dto.actions;

import ru.vhistory.ikurbanov.constant.State;

import java.util.Date;

public abstract class HistoryAction {

    public abstract String getName();
    public abstract String getUser();
    public abstract Date getTime();
    public abstract State getState();
    public abstract String getLocalizedName();

}
