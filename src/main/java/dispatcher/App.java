package dispatcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {

    private static final Logger logger = LogManager.getLogger(App.class.getName());

    public static void main(String[] args){

        System.out.println("hello world");
        logger.error("test logs");
        logger.warn("test");
        logger.debug("test logs");


    }

}
