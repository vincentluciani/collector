package manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reader.websiteReader.Entity;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LauncherApp {

    private static final Logger logger = LogManager.getLogger(LauncherApp.class.getName());

    public static void main(String[] args) throws InterruptedException {

        logger.info("Starting processing main");
        LocalTime startTime = LocalTime.now();
        String startMessage = String.format("start:%s",startTime.toString());
        logger.info(startMessage);

        List<String> listOfLogicalNodes = new ArrayList<String>();
        String basePath="";
        String task="";
        String lastBatch="";

        Pattern nodeListPattern = Pattern.compile("nodeList=(.*)");
        Pattern basePathPattern = Pattern.compile("basePath=(.*)");
        Pattern taskPattern = Pattern.compile("task=(.*)");
        Pattern lastBatchPattern = Pattern.compile("lastBatch=(.*)");

        for ( int i=0; i < args.length;i++){

            Matcher nodeListMatcher = nodeListPattern.matcher(args[i]);
            Matcher basePathMatcher = basePathPattern.matcher(args[i]);
            Matcher taskMatcher = taskPattern.matcher(args[i]);
            Matcher lastBatchMatcher = lastBatchPattern.matcher(args[i]);

            String listOfNodes="";

            if (nodeListMatcher.find()) {
                listOfNodes = nodeListMatcher.group(1);
                listOfLogicalNodes = Arrays.asList(listOfNodes.split("\\s*,\\s*"));
            } else if (basePathMatcher.find()){
                basePath = basePathMatcher.group(1);
            } else if (taskMatcher.find()){
                task = taskMatcher.group(1);
            } else if (lastBatchMatcher.find()){
                lastBatch = lastBatchMatcher.group(1);
            }
        }

        List<NewThread> listOfThreads = new ArrayList<NewThread>();

        for ( String currentLogicalNode : listOfLogicalNodes){
            NewThread currentThread = new NewThread(currentLogicalNode,basePath,task,lastBatch);
            listOfThreads.add(currentThread);
        }

        for ( NewThread currentThreadToJoin : listOfThreads){
            currentThreadToJoin.thread.join();
        }


        logger.info("Ended processing main");
        LocalTime endTime = LocalTime.now();
        String finishMessage = String.format("finished processing:%s",endTime.toString());
        logger.info(finishMessage);

        Duration durfirst = Duration.between(startTime, endTime);
        long millis = durfirst.toMillis();

        String duration1 = String.format("Duration 1: %02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

        logger.info(duration1);

    }

}

