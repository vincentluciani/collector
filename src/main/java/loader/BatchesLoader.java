package loader;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import manager.LogicalNodeConfigurationManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class BatchesLoader {


    private LogicalNodeConfigurationManager logicalNodeConfigurationManager;
    private static final Logger logger = LogManager.getLogger(BatchesLoader.class.getName());

    public BatchesLoader(LogicalNodeConfigurationManager logicalNodeConfigurationManager) {
        this.logicalNodeConfigurationManager = logicalNodeConfigurationManager;
    }

    public boolean load() {

        try (DirectoryStream<Path> workingDirectoryStream = Files.newDirectoryStream(this.logicalNodeConfigurationManager.getBatchForUploadBasePath(), "batch_*"))
        {
            for (Path currentFileOrDirectory : workingDirectoryStream)
            {
                String requestBody = "";
                StringBuilder stringBuilder = new StringBuilder(requestBody);

                List<String> lines = FileUtils.readLines(currentFileOrDirectory.toFile(), StandardCharsets.UTF_8);
                for (java.lang.String line : lines)
                {
                    stringBuilder.append(line).append("\n");
                }
                BatchLoader batchLoader = new BatchLoader();
                requestBody = stringBuilder.toString();
                String statusString = batchLoader.loadIndividualBatch(logicalNodeConfigurationManager, requestBody, false);

                if (statusString == "SUCCESS")
                {
                    logger.info("Batch:" + currentFileOrDirectory.getFileName() + " loaded successfully");
                } else
                    logger.error(statusString);
                }
        }  catch (IOException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }
}