package dispatcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reader.DatabaseToFileConverter;
import reader.LogicalNodeConfigurationManager;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;

public class App {

    private static final Logger logger = LogManager.getLogger(App.class.getName());

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {

        logger.info("Starting processing");

        LogicalNodeConfigurationManager readerConfigurationManager = new LogicalNodeConfigurationManager(Paths.get("C:\\test_java"),"myse_id");

        DatabaseToFileConverter databaseToFileConverter = new DatabaseToFileConverter(readerConfigurationManager,"4007129887538");

        logger.info("Ended processing");
    }

}

