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
public class Create extends HistoryAction {
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy H:m:s a");
    private final static Pattern p = Pattern.compile("history = (.+) - user: (.+) time: (.+) state: (.+) revisioned from:");

    @Getter
    private final String localizedName = "Создание объекта";
    @Getter
    private String name = "Create";
    @Getter
    private State state;
    @Getter
    private String user;
    @Getter
    private Date time;
    @Getter
    private String revisionedFrom = "";


    public Create(String parsableString) throws IllegalArgumentException {

        Matcher m = p.matcher(parsableString);
        if (m.matches()) {
            this.user = m.group(2).trim();
            this.state = State.valueOf(m.group(4).trim().toUpperCase());
            try {
                this.time = simpleDateFormat.parse(m.group(3));
            } catch (ParseException e) {
                throw new IllegalArgumentException("Unable to parse date from String:" + parsableString + " to object:" + this.name);
            }
        } else {
            throw new IllegalArgumentException("Unable to map String: " + parsableString + " to object:" + this.name);
        }
    }

}
