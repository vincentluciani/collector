package DispatcherTest;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogFileAnalyzer {

    Pattern pattern = Pattern.compile("(.*) (.*);(.*);.*;(.*)");
    File logFile;

    LogFileAnalyzer(File logFile) throws IOException {
        this.logFile = logFile;
        // Empty the log file in case it was written during a previous unit test
        PrintWriter pw = new PrintWriter(logFile);
        pw.close();
    }

    public ArrayList<LogFileLineContent> getLogFileContent() throws IOException {

        List<String> lines = FileUtils.readLines(logFile, "UTF-8");
        ArrayList<LogFileLineContent> logFileContent = new ArrayList<LogFileLineContent>();

        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            LogFileLineContent logFileCurrentLine = new LogFileLineContent();
            while (matcher.find()) {
                logFileCurrentLine.initialize(matcher.group(1),
                        matcher.group(2),
                        matcher.group(3),
                        matcher.group(4));
            }
            if (!logFileCurrentLine.isEmpty()){
                logFileContent.add(logFileCurrentLine);
            }
        }
        return logFileContent;
    }


    public class LogFileLineContent {
        String date;
        String time;
        String message;
        String level;

        public void initialize(String date,String time,String level,String message){
            this.date = date;
            this.time = time;
            this.level = level;
            this.message = message;
        }

        LogFileLineContent(){
        }

        public boolean isEmpty(){
            if ( this.date != null && this.time!=null && this.level!=null && this.message!=null){
                return false;
            }
            return true;
        }
    }
}
