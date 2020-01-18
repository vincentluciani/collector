package dispatcher;

import lombok.SneakyThrows;
import reader.DatabaseToFileConverter;
import reader.LogicalNodeConfigurationManager;

import java.nio.file.Paths;
import java.sql.SQLException;

class NewThread implements Runnable {

    String logicalNode;
    Thread thread;

    NewThread(String threadName){
        logicalNode = threadName;
        thread = new Thread(this,this.logicalNode);
        System.out.println("thread:"+ thread);
        thread.start();
    }

    @SneakyThrows
    public void run(){

        System.out.println("Node:"+logicalNode+" starting");
        LogicalNodeConfigurationManager readerConfigurationManager = new LogicalNodeConfigurationManager(Paths.get("C:\\test_java"),logicalNode);

        try {
            DatabaseToFileConverter databaseToFileConverter = new DatabaseToFileConverter(readerConfigurationManager,"4007129960313");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Node:"+logicalNode+" ended");
    }



}
