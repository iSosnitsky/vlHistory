package ru.vhistory.ikurbanov.dto.actions;

import ru.vhistory.ikurbanov.constant.State;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Create extends HistoryAction{
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

    public String getRevisionedFrom() {
        return revisionedFrom;
    }


    public String toJson() {
        return "{" +
                "name:'" + name + '\'' +
                ", state:" + state +
                ", user:'" + user + '\'' +
                ", time:" + simpleDateFormat.format(time) +
                ", revisionedFrom:'" + revisionedFrom + '\'' +
                '}';
    }

    private String name="Create";
    private State state;
//    private User user;
    private String user;
    private Date time;
    private String revisionedFrom="";
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy H:m:s a");
    private final static Pattern p = Pattern.compile("history = (.+) - user: (.+) time: (.+) state: (.+) revisioned from:");


    public Create(String parsableString) throws IllegalArgumentException{

        Matcher m = p.matcher(parsableString);
        if (m.matches()){
            this.user = m.group(2).trim();
            this.state = State.valueOf(m.group(4).trim().toUpperCase());
            try{
                this.time = simpleDateFormat.parse(m.group(3));
            } catch (ParseException e){
                throw new IllegalArgumentException("Unable to parse date from String:"+parsableString+" to object:"+this.name);
            }

//            this.test = ("Действие: "+m.group(1)+", Юзер: "+m.group(2)+" Время: " + m.group(3) +", Состояние: "+ m.group(4));
        } else {
            throw new IllegalArgumentException("Unable to map String: "+parsableString+" to object:"+ this.name);
        }
    }

    @Override
    public String toString() {
        return "Create{" +
                "name='" + name + '\'' +
                ", state=" + state +
                ", user='" + user + '\'' +
                ", time=" + simpleDateFormat.format(time)+
                ", revisionedFrom='" + revisionedFrom + '\'' +
                '}';
    }
}
