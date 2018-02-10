package ru.vhistory.ikurbanov.dto.actions;


import ru.vhistory.ikurbanov.constant.State;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Connect extends HistoryAction  {
    private final String name = "Connect";
    private State state;
    private String user;
    private Date time;
    private String connectionSubject;
    private String connectionObject;
    private String forUser;
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy H:m:s a");
    private final static Pattern p = Pattern.compile("history = connect (.*) (?:from|to) (.*)- user:(.+) time:(.+) state: (\\w*)(?:\\s*for user:(.+))?");


    public Connect (String parsableString) throws IllegalArgumentException {
        Matcher m = p.matcher(parsableString);
        if (m.matches()) {
//            this.name = m.group(1);
            this.user = m.group(3).trim();
            this.state = State.valueOf(m.group(5).trim().toUpperCase());
            try {
                this.time = simpleDateFormat.parse(m.group(4));
            } catch (ParseException e) {
                throw new IllegalArgumentException("Unable to parse date from String:"+parsableString+" to object:"+this.name);
            }
            if (parsableString.contains(" from ")){
                this.connectionObject = m.group(1).trim();
                this.connectionSubject = m.group(2).trim();
            } else {
                this.connectionObject = m.group(2).trim();
                this.connectionSubject = m.group(1).trim();
            }

            forUser = (m.group(6)==null) ? "" : m.group(6).trim();

//            this.test = ("Действие: "+m.group(1)+", Юзер: "+m.group(2)+" Время: " + m.group(3) +", Состояние: "+ m.group(4));
        } else {
            throw new IllegalArgumentException("Unable to map String: "+parsableString+" to object:"+ this.name);
        }

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public Date getTime() {
        return time;
    }

    public String getConnectionSubject() {
        return connectionSubject;
    }

    public String getConnectionObject() {
        return connectionObject;
    }

    @Override
    public String toString() {
        return "Connect{" +
                "name='" + name + '\'' +
                ", state=" + state +
                ", user='" + user + '\'' +
                ", time=" + simpleDateFormat.format(time)+
                ", connectionSubject='" + connectionSubject + '\'' +
                ", connectionObject='" + connectionObject + '\'' +
                '}';
    }
}
