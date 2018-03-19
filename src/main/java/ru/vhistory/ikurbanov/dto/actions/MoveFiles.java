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
public class MoveFiles extends HistoryAction {
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy H:m:s a");
    private final static Pattern p = Pattern.compile("history = move files - user:(.+) time:(.+) state:(.+) format:(.+) file:(.*?) (?:to|from) bus: (.*?)(?: from )?format: (.+)");

    @Getter
    private final String localizedName="Перемещение";
    @Getter
    private final String name = "MoveFiles";
    @Getter
    private State state;
    @Getter
    private String user;
    @Getter
    private Date time;
    @Getter
    private String fileName;
    @Getter
    private String originalFormat;
    @Getter
    private String destination;
    @Getter
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
        } else {
            throw new IllegalArgumentException("Unable to map String: "+parsableString+" to object:"+ this.name);
        }
    }
}
