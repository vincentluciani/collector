package dispatcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reader.DatabaseToFileConverter;
import reader.LogicalNodeConfigurationManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class App {

    private static final Logger logger = LogManager.getLogger(App.class.getName());

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, InterruptedException {

        logger.info("Starting processing main");
        LocalTime startTime = LocalTime.now();
        System.out.println("start:" + startTime.toString());

        NewThread thread1 = new NewThread("myse_id");
       NewThread thread2 = new NewThread("myse_test");

        thread1.thread.join();
        thread2.thread.join();

 /*       String baseDirectory = "C:\\test_java\\test";

        Path basePath = Paths.get(baseDirectory);

        DispatcherConfigurationManager dispatcherConfigurationManager = new DispatcherConfigurationManager(basePath);

        FilesDispatcher filesDispatcher = new FilesDispatcher(dispatcherConfigurationManager);

        filesDispatcher.scanNewFilesDirectory();

        LocalTime endTimeScanNew = LocalTime.now();
        System.out.println("finished scanning new directory:"+endTimeScanNew.toString());

        filesDispatcher.scanOldFilesDirectory();
*/
        logger.info("Ended processing main");
        LocalTime endTime = LocalTime.now();
        System.out.println("finished processing"+endTime.toString());


    /*    Duration durfirst = Duration.between(startTime, endTimeScanNew);
        long millis = durfirst.toMillis();

        Duration dursecond = Duration.between(endTimeScanNew, endTime);
        long secondmillis = dursecond.toMillis();
*/

        Duration durfirst = Duration.between(startTime, endTime);
        long millis = durfirst.toMillis();

        String duration1 = String.format("Duration 1: %02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

        System.out.println(duration1);

        /*
        String duration2 = String.format("Duration 2: %02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(secondmillis),
                TimeUnit.MILLISECONDS.toMinutes(secondmillis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(secondmillis)),
                TimeUnit.MILLISECONDS.toSeconds(secondmillis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(secondmillis)));


        System.out.println(duration2);
*/
    }

}

