package ru.vhistory.ikurbanov.dto.actions;

import lombok.Getter;
import lombok.ToString;
import ru.vhistory.ikurbanov.constant.State;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ToString
public class ChangeName extends HistoryAction {
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy H:m:s a");
    private static Pattern p = Pattern.compile("history = change name - user:(.+)time:(.+)state:(.+ ) name:(.+)was:(.+)? revision:");

    @Getter
    private final String name = "ChangeName";
    @Getter
    private final String localizedName="Переименование";
    @Getter
    private State state;
    @Getter
    private String user;
    @Getter
    private Date time;
    @Getter
    private String currentFileName;
    @Getter
    private String previousFileName;


    public ChangeName(String parsableString) throws IllegalArgumentException{
        if(!parsableString.contains(" was:")){
            //a workaround. I don't know how to handle missing "was" clause with regex yet.
            parsableString = parsableString+" was:";
        };
        Matcher m = p.matcher(parsableString);
        if (m.matches()){
            this.user = m.group(1).trim();
            this.state = State.valueOf(m.group(3).trim().toUpperCase());
            try{
                this.time = simpleDateFormat.parse(m.group(2).trim());
            } catch (ParseException e){
                throw new IllegalArgumentException("Unable to parse date from String:"+parsableString+" to object:"+this.name);
            }
            this.currentFileName = m.group(4).trim();
            this.previousFileName = m.group(5).trim();
        } else {
            throw new IllegalArgumentException("Unable to map String: "+parsableString+" to object:"+ this.name);
        }
    }


}
