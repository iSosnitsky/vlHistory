package ru.vhistory.ikurbanov.dto.actions;

import ru.vhistory.ikurbanov.constant.State;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lock extends HistoryAction {
    private static Pattern p = Pattern.compile("history = lock - user:(.+) time:(.+)state:(.+)");
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy H:m:s a");
    private String name = "Lock";
    private String user;
    private Date time;
    private State state;

    public Lock(String parsableString) throws IllegalArgumentException{
        Matcher m = p.matcher(parsableString);
        if (m.matches()) {
            this.user = m.group(1).trim();
            try {
                this.time = simpleDateFormat.parse(m.group(2));
            } catch (ParseException e) {
                throw new IllegalArgumentException("Unable to parse date from String:"+parsableString+" to object:"+this.name);
            }
            this.state = State.valueOf(m.group(3).trim().toUpperCase());
        } else {
            throw new IllegalArgumentException("Unable to map String: "+parsableString+" to object:"+ this.name);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public Date getTime() {
        return time;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public String toString() {
        return "Lock{" +
                "name='" + name + '\'' +
                ", user='" + user + '\'' +
                ", time=" + simpleDateFormat.format(time )+
                ", state=" + state +
                '}';
    }
}
