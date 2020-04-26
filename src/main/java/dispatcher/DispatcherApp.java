package dispatcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class DispatcherApp {

    private static final Logger logger = LogManager.getLogger(DispatcherApp.class.getName());

    public static void main(String[] args) throws InterruptedException {

        logger.info("Starting processing main");
        LocalTime startTime = LocalTime.now();
        String startMessage = String.format("start:%s",startTime.toString());
        logger.info(startMessage);

        NewThread thread1 = new NewThread("zh_CN");
      //  NewThread thread2 = new NewThread("zh_TW");

        thread1.thread.join();
     //   thread2.thread.join();

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

