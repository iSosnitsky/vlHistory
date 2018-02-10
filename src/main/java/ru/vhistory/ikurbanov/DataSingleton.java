package ru.vhistory.ikurbanov;

import lombok.Getter;
import ru.vhistory.ikurbanov.constant.DataObjectType;
import ru.vhistory.ikurbanov.dto.DataObject;
import ru.vhistory.ikurbanov.dto.actions.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DataSingleton {
    private static final DataSingleton INSTANCE = new DataSingleton();

    @Getter
    private List<DataObject> dataObjects;
    @Getter
    private List<String> users;

    public static DataSingleton getInstance() {
        return INSTANCE;
    }

    private DataSingleton() {
        ClassLoader classLoader = getClass().getClassLoader();
        List<List<String>> chunks;
        File dataFile = new File(classLoader.getResource("history.txt").getFile());
        File usersFile = new File(classLoader.getResource("users.txt").getFile());
        try {
            List<String> allUsers = Files.readAllLines(usersFile.toPath(), Charset.forName("UTF-8"));
            users = allUsers.stream().map(String::trim).collect(Collectors.toList());
            List<String> allLines = Files.readAllLines(dataFile.toPath(), Charset.forName("UTF-8"));
            chunks = splitIntoChunks(allLines, "businessobject");
            this.dataObjects = chunks.stream().map(DataSingleton::parseChunk).collect(Collectors.toList());
//            this.dataObjects.forEach(x -> System.out.println(x.toString()));
//            chunks.forEach(strings -> System.out.println(strings.get(0)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static HistoryAction parseString(String string) throws IllegalArgumentException {
        HistoryAction result = null;
        string = string.trim();
        if (string.contains("(checkout)")) {
            string = string.replace("(checkout)", "checkout");
        }
        final Pattern p = Pattern.compile("history = (\\w+).*");
        final Matcher m = p.matcher(string);
        if (!m.matches()) {
            throw new IllegalArgumentException("Unable to map String: " + string + " to object: HistoryAction");
        }
        try {


            String cString = m.group(1).trim();
            switch (cString) {
                case "connect":
                    result = new Connect(string);
                    break;
                case "create":
                    result = new Create(string);
                    break;
                case "modify":
                    result = new Modify(string);
                    break;
                case "checkin":
                    result = new Checkin(string);
                    break;
                case "checkout":
                    result = new Checkout(string);
                    break;
                case "move":
                    result = new MoveFiles(string);
                    break;
                case "delete":
                    result = new DeleteFile(string);
                    break;
                case "disconnect":
                    result = new Disconnect(string);
                    break;
                case "lock":
                    result = new Lock(string);
                    break;
                case "change":
                    result = new ChangeName(string);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static DataObject parseChunk(List<String> chunk) throws IllegalArgumentException {
        final DataObject result;
        final Pattern p = Pattern.compile("(\\w+) (\\w+) (.+) -");
        final Matcher m = p.matcher(chunk.get(0));
        if (m.matches()) {
            final String name = m.group(3).trim();
            final DataObjectType type = DataObjectType.valueOf(m.group(2).trim().toUpperCase());
            final List<HistoryAction> historyActions = chunk.stream().filter(x -> x.contains("history")).map(DataSingleton::parseString).collect(Collectors.toList());
            result = new DataObject(name, type, historyActions);
        } else {
            throw new IllegalArgumentException("Unable to map String: " + chunk.get(0) + " to object: DataObject");
        }
        return result;

    }

    ;

    private static List<List<String>> splitIntoChunks(List<String> allLines, String delimiter) {
        List<List<String>> result = new ArrayList<List<String>>();
        try {


            int i = 0;
            int fromIndex = 0;
            int toIndex = 0;
            for (String line : allLines) {
                i++;
                if (line.startsWith(delimiter) && i != 1) { // To skip the first line and the check next delimiter
                    toIndex = i - 1;
                    result.add(allLines.subList(fromIndex, toIndex));
                    fromIndex = i - 1;
                }
            }
            result.add(allLines.subList(fromIndex, allLines.size()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //Public static functions are a bad practice. I know.
    public static List<DataObject> StringListToDataObject(List<String> allLines) {
        List<List<String>> chunks = splitIntoChunks(allLines, "businessobject");
        return chunks.stream().map(DataSingleton::parseChunk).collect(Collectors.toList());
    }

}
