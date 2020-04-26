package loader;

import dispatcher.DispatcherApp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import manager.LogicalNodeConfigurationManager;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class LoaderApp {

    private static final Logger logger = LogManager.getLogger(DispatcherApp.class.getName());

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, InterruptedException {

        logger.info("Starting processing main");
        LocalTime startTime = LocalTime.now();
        System.out.println("start:" + startTime.toString());

        LogicalNodeConfigurationManager logicalNodeConfigurationManager = new LogicalNodeConfigurationManager(Paths.get("C:\\test_java"),"en_ID");

      /*  String baseDirectory = "C:\\test_java\\output5"; */

        BatchesLoader batchesLoader = new BatchesLoader(logicalNodeConfigurationManager);

        boolean result = batchesLoader.load();


        logger.info("Ended processing main");
        LocalTime endTime = LocalTime.now();
        System.out.println("finished processing"+endTime.toString());


        Duration durfirst = Duration.between(startTime, endTime);
        long millis = durfirst.toMillis();

        String duration1 = String.format("Duration 1: %02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

        System.out.println(duration1);

    }

}

