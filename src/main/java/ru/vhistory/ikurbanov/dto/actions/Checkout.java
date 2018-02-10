package ru.vhistory.ikurbanov.dto.actions;

import ru.vhistory.ikurbanov.constant.State;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Checkout extends HistoryAction {

    private final String name = "Checkout";
    private State state;
    private String user;
    private Date time;
    private String format;
    private String fileName;
    private String comment;
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy H:m:s a");
    private final static Pattern p = Pattern.compile("history = (.+) - user:(.+)time:(.+)state:(.+) (?:comment:(.*))? format: (.+) file:(.+)");


    public Checkout(String parsableString) throws IllegalArgumentException{
        Matcher m = p.matcher(parsableString);
        if (m.matches()) {
//            this.name = m.group(1);
            this.user = m.group(2).trim();
            this.state = State.valueOf(m.group(4).trim().toUpperCase());
            try {
                this.time = simpleDateFormat.parse(m.group(3));
            } catch (ParseException e) {
                throw new IllegalArgumentException("Unable to parse date from String:"+parsableString+" to object:"+this.name);
            }
            this.format = m.group(6).trim();
            this.fileName = m.group(7).trim();
            this.comment = (m.group(5)==null) ? "" : m.group(5);

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

    public String getFormat() {
        return format;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public String toString() {
        return "Checkout{" +
                "name='" + name + '\'' +
                ", state=" + state +
                ", user='" + user + '\'' +
                ", time=" + simpleDateFormat.format(time)+
                ", format='" + format + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
