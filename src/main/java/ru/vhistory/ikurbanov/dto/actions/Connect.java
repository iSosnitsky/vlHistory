package ru.vhistory.ikurbanov.dto.actions;


import lombok.Getter;
import ru.vhistory.ikurbanov.constant.State;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Connect extends HistoryAction  {
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy H:m:s a");
    private final static Pattern p = Pattern.compile("history = connect (.*) (?:from|to) (.*)- user:(.+) time:(.+) state: (\\w*)(?:\\s*for user:(.+))?");

    //Не знаю, что в контексте программы значит Connect
    @Getter
    private final String localizedName="Соединение";
    @Getter
    private final String name = "Connect";
    @Getter
    private State state;
    @Getter
    private String user;
    @Getter
    private Date time;
    @Getter
    private String connectionSubject;
    @Getter
    private String connectionObject;
    @Getter
    private String forUser;


    public Connect (String parsableString) throws IllegalArgumentException {
        Matcher m = p.matcher(parsableString);
        if (m.matches()) {
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
        } else {
            throw new IllegalArgumentException("Unable to map String: "+parsableString+" to object:"+ this.name);
        }

    }
}
