package ru.vhistory.ikurbanov.dto.actions;

import ru.vhistory.ikurbanov.constant.State;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoveFiles extends HistoryAction {

    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy H:m:s a");
    private final static Pattern p = Pattern.compile("history = move files - user:(.+) time:(.+) state:(.+) format:(.+) file:(.*?) (?:to|from) bus: (.*?)(?: from )?format: (.+)");

    private final String name = "MoveFiles";
    private State state;
    private String user;
    private Date time;
    private String fileName;
    private String originalFormat;
    private String destination;
    private String format;

    public MoveFiles(String parsableString) throws IllegalArgumentException{
        Matcher m = p.matcher(parsableString);
        if (m.matches()) {
            this.user = m.group(1).trim();
            try {
                this.time = simpleDateFormat.parse(m.group(2));
            } catch (ParseException e) {
                throw new IllegalArgumentException("Unable to parse date from String:"+parsableString+" to object:"+this.name);
            }
            this.state = State.valueOf(m.group(3).trim().toUpperCase());
            this.format = m.group(4).trim();
            this.fileName= m.group(5).trim();
            this.destination = m.group(6).trim();
            this.originalFormat = m.group(7).trim();

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

    public String getFileName() {
        return fileName;
    }

    public String getOriginalFormat() {
        return originalFormat;
    }

    public String getDestination() {
        return destination;
    }

    public String getFormat() {
        return format;
    }


    @Override
    public String toString() {
        return "MoveFiles{" +
                "name='" + name + '\'' +
                ", state=" + state +
                ", user='" + user + '\'' +
                ", time=" + simpleDateFormat.format(time) +
                ", fileName='" + fileName + '\'' +
                ", originalFormat='" + originalFormat + '\'' +
                ", destination='" + destination + '\'' +
                ", format='" + format + '\'' +
                '}';
    }
}
