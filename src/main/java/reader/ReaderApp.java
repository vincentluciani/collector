package reader;

import lombok.SneakyThrows;
import manager.LogicalNodeConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;

public class ReaderApp {

        String logicalNode;
        Thread thread;
        private static final Logger logger = LogManager.getLogger(ReaderApp.class.getName());

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, InterruptedException {

            String logicalNode = "vince";

            String lastProcessedIdentification="0";
            String startingMessage = String.format("Node: %s starting",logicalNode);
            logger.info(startingMessage);
            LogicalNodeConfigurationManager readerConfigurationManager = new LogicalNodeConfigurationManager(Paths.get("C:\\test_java"),logicalNode);

            try {
                Reader reader = ReaderFactory.getReader(readerConfigurationManager.getLogicalNodeType(),readerConfigurationManager,lastProcessedIdentification);
                reader.readAndOutputToUniversalFile();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
            String endingMessage = String.format("Node: %s ended",logicalNode);
            logger.info(endingMessage);
        }



    }

