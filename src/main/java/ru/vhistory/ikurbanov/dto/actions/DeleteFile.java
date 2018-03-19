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
public class DeleteFile extends HistoryAction {
    private static Pattern p = Pattern.compile("history = delete file - user:(.+) time:(.+) state:(.+) format:(.+) file:(.*)");
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy H:m:s a");


    @Getter
    private final String localizedName="Удаление";
    @Getter
    private final String name = "DeleteFile";
    @Getter
    private String user;
    @Getter
    private Date time;
    @Getter
    private State state;
    @Getter
    private String format;
    @Getter
    private String file;

    public DeleteFile(String parsableString) throws IllegalArgumentException{
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
            this.file= m.group(5).trim();
        } else {
            throw new IllegalArgumentException("Unable to map String: "+parsableString+" to object:"+ this.name);
        }
    }


}
