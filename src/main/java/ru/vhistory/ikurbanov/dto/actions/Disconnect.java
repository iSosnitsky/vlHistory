package ru.vhistory.ikurbanov.dto.actions;

import ru.vhistory.ikurbanov.constant.State;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Disconnect extends HistoryAction {
    private final String name = "Disconnect";
    private State state;
    private String user;
    private Date time;
    private String disconnectionSubject;
    private String disconnectionObject;
    private String forUser;
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy H:m:s a");
    private final static Pattern p = Pattern.compile("history = disconnect (.*) (?:from|to) (.*)- user:(.+) time:(.+) state: (\\w*)(?:\\s*for user:(.+))?");


    public Disconnect (String parsableString) throws IllegalArgumentException {
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
                this.disconnectionObject = m.group(1).trim();
                this.disconnectionSubject = m.group(2).trim();
            } else {
                this.disconnectionObject = m.group(2).trim();
                this.disconnectionSubject = m.group(1).trim();
            }

            forUser = (m.group(6)==null) ? "" : m.group(6).trim();


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

    public String getDisconnectionSubject() {
        return disconnectionSubject;
    }

    public String getDisconnectionObject() {
        return disconnectionObject;
    }

    public String getForUser() {
        return forUser;
    }
}
