package manager;

import dispatcher.DispatcherConfigurationManager;
import dispatcher.FilesDispatcher;
import loader.BatchesLoader;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reader.Reader;
import reader.ReaderFactory;
import writter.BatchCreator;

import java.nio.file.Paths;
import java.sql.SQLException;

class NewThread implements Runnable {

    String logicalNode;
    String basePath;
    Thread thread;
    String task;

    private static final Logger logger = LogManager.getLogger(NewThread.class.getName());

    NewThread(String threadName,String basePath, String task){
        logicalNode = threadName;
        this.basePath = basePath;
        this.task = task;
        thread = new Thread(this,this.logicalNode);
        String threadMessage = String.format("thread: %s",thread);
        logger.info(threadMessage);
        thread.start();
    }

    @SneakyThrows
    public void run(){

        String lastProcessedIdentification="0";
        String startingMessage = String.format("Node: %s task %s starting",logicalNode,this.task);
        logger.info(startingMessage);
        LogicalNodeConfigurationManager logicalNodeConfigurationManager = new LogicalNodeConfigurationManager(Paths.get(this.basePath),logicalNode);

        try {

            if ( this.task.equals("read")) {
                Reader reader = ReaderFactory.getReader(logicalNodeConfigurationManager.getLogicalNodeType(), logicalNodeConfigurationManager, lastProcessedIdentification);
                reader.readAndOutputToUniversalFile();
            } else if (this.task.equals("write")){
                BatchCreator batchCreator = new BatchCreator(logicalNode, this.basePath, logicalNodeConfigurationManager);
                for (int i=0;i<9;i++) {
                    batchCreator.toFile();
                }
            } else if (this.task.equals("load")){
                BatchesLoader batchesLoader = new BatchesLoader(logicalNodeConfigurationManager,"silent");
                boolean result = batchesLoader.load();
            } else if (this.task.equals("dispatch")){
                DispatcherConfigurationManager dispatcherConfigurationManager = new DispatcherConfigurationManager(Paths.get(this.basePath));

                FilesDispatcher filesDispatcher = new FilesDispatcher(dispatcherConfigurationManager);

                filesDispatcher.scanNewFilesDirectory();
                filesDispatcher.scanOldFilesDirectory();

            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        String endingMessage = String.format("Node: %s task %s ended",logicalNode,this.task);
        logger.info(endingMessage);
    }



}
