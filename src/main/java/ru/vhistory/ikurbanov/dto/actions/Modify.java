package ru.vhistory.ikurbanov.dto.actions;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import ru.vhistory.ikurbanov.constant.State;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ToString
public class Modify extends HistoryAction{
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy H:m:s a");
    private final static Pattern p = Pattern.compile("history = (.+) - user:(.+)time:(.+)state:(.+ ) (.+):(.+)was:(.+)?");

    @Getter
    private final String localizedName="Изменение";
    @Getter
    private final String name = "Modify";
    @Getter
    private State state;
    @Getter
    private String user;
    @Getter
    private Date time;
    @Getter
    private ModifiedValue affectedValue;


    public Modify(String parsableString) throws IllegalArgumentException{
        if(!parsableString.contains(" was:")){
            parsableString = parsableString+" was:";
        }
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
        } else {
            throw new IllegalArgumentException("Unable to map String: "+parsableString+" to object:"+ this.name);
        }
    }

    @Data
    @ToString
    private class ModifiedValue{
        private String name;
        private String currentValue;
        private String previousValue;

        ModifiedValue(String name, String currentValue, String previousValue) {
            this.name = name;
            this.currentValue = currentValue;
            this.previousValue = previousValue;
        }
    }
}
