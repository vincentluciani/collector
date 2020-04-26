package writter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import manager.LogicalNodeConfigurationManager;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class writterApp {

    private static final Logger logger = LogManager.getLogger(writterApp.class.getName());

    public static void main(String[] args) throws  IOException {

        logger.info("Starting processing main");
        LocalTime startTime = LocalTime.now();

        String startingMessage = String.format("start:%s",startTime.toString());
        logger.info(startingMessage);

        LogicalNodeConfigurationManager logicalNodeConfigurationManager = new LogicalNodeConfigurationManager(Paths.get("C:\\test_java"),"en_ID");

        String baseDirectory = "C:\\test_java\\output";

        BatchCreator batchCreator = new BatchCreator("vince", baseDirectory, logicalNodeConfigurationManager);

        for (int i=0;i<9;i++) {
            batchCreator.toFile();
         }

        logger.info("Ended processing main");
        LocalTime endTime = LocalTime.now();
        String endMessage = String.format("finished processing: %s",endTime.toString());
        logger.info(endMessage);

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

