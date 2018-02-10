package ru.vhistory.ikurbanov.dto.actions;

import ru.vhistory.ikurbanov.constant.State;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Modify extends HistoryAction{
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


    @Override
    public String toString() {
        return "Modify{" +
                "name:'" + name + '\'' +
                ", state=" + state +
                ", user='" + user + '\'' +
                ", time='" + simpleDateFormat.format(time) +'\''+
                ", affectedValue=" + affectedValue.toString() +
                '}';
    }

    public String toJson(){
        return "Modify{" +
                "name:'" + name + '\'' +
                ", state:" + state +
                ", user:'" + user + '\'' +
                ", time:'" + simpleDateFormat.format(time) +'\''+
                ", affectedValue:" + affectedValue.toString() +
                '}';
    }

    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy H:m:s a");
    private final static Pattern p = Pattern.compile("history = (.+) - user:(.+)time:(.+)state:(.+ ) (.+):(.+)was:(.+)?");

    private final String name = "Modify";
    private State state;
    private String user;
    private Date time;
    private ModifiedValue affectedValue;


    public Modify(String parsableString) throws IllegalArgumentException{
        if(!parsableString.contains(" was:")){
            //a workaround. I don't know how to handle missing "was" clause with regex yet.
            parsableString = parsableString+" was:";
        };
        Matcher m = p.matcher(parsableString);
        if (m.matches()){
            this.user = m.group(2).trim();
            this.state = State.valueOf(m.group(4).trim().toUpperCase());
            try{
                this.time = simpleDateFormat.parse(m.group(3).trim());
            } catch (ParseException e){
                throw new IllegalArgumentException("Unable to parse date from String:"+parsableString+" to object:"+this.name);
            }
            this.affectedValue = new ModifiedValue(m.group(5), m.group(6),(m.group(7)==null)? "" : m.group(7));

//            this.test = ("Действие: "+m.group(1)+", Юзер: "+m.group(2)+" Время: " + m.group(3) +", Состояние: "+ m.group(4));
        } else {
            throw new IllegalArgumentException("Unable to map String: "+parsableString+" to object:"+ this.name);
        }
    }


    private class ModifiedValue{
        private String name;
        private String currentValue;
        private String previousValue;

        public ModifiedValue(String name, String currentValue, String previousValue) {
            this.name = name;
            this.currentValue = currentValue;
            this.previousValue = previousValue;
        }

        @Override
        public String toString() {
            return "{" +
                    "name:'" + name + '\'' +
                    ", currentValue:'" + currentValue + '\'' +
                    ", previousValue:'" + previousValue + '\'' +
                    '}';
        }
    }
}
