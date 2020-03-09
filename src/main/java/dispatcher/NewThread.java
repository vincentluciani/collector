package dispatcher;

import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import manager.LogicalNodeConfigurationManager;
import reader.Reader;
import reader.ReaderFactory;

import java.nio.file.Paths;
import java.sql.SQLException;

class NewThread implements Runnable {

    String logicalNode;
    Thread thread;
    private static final Logger logger = LogManager.getLogger(NewThread.class.getName());

    NewThread(String threadName){
        logicalNode = threadName;
        thread = new Thread(this,this.logicalNode);
        String threadMessage = String.format("thread: %s",thread);
        logger.info(threadMessage);
        thread.start();
    }

    @SneakyThrows
    public void run(){

        String startingMessage = String.format("Node: %s starting",logicalNode);
        logger.info(startingMessage);
        LogicalNodeConfigurationManager readerConfigurationManager = new LogicalNodeConfigurationManager(Paths.get("C:\\test_java"),logicalNode);

        try {
            Reader reader = ReaderFactory.getReader("oracle",readerConfigurationManager,"4007129960313");
            reader.readAndOutputToUniversalFile();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        String endingMessage = String.format("Node: %s ended",logicalNode);
        logger.info(endingMessage);
    }



}
