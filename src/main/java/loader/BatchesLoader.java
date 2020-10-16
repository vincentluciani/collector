package loader;

import manager.LogicalNodeConfigurationManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class BatchesLoader {

    private static final String LOAD_SUCCESS_MESSAGE_PATTERN = "Batch: %s loaded successfully";
    private static final Logger logger = LogManager.getLogger(BatchesLoader.class.getName());
    private String reportingType;
    private LogicalNodeConfigurationManager logicalNodeConfigurationManager;

    public BatchesLoader(LogicalNodeConfigurationManager logicalNodeConfigurationManager, String reportingType) {
        this.logicalNodeConfigurationManager = logicalNodeConfigurationManager;
        this.reportingType = reportingType;
    }

    public boolean loadAllBatches() {

        try (DirectoryStream<Path> workingDirectoryStream = Files.newDirectoryStream(this.logicalNodeConfigurationManager.getBatchForUploadBasePath(), "batch_*")) {
            for (Path currentFileOrDirectory : workingDirectoryStream) {
                String requestBody = "";
                StringBuilder stringBuilder = new StringBuilder(requestBody);

                List<String> lines = FileUtils.readLines(currentFileOrDirectory.toFile(), StandardCharsets.UTF_8);
                for (java.lang.String line : lines) {
                    stringBuilder.append(line).append("\n");
                }
                BatchLoader batchLoader = new BatchLoader();
                requestBody = stringBuilder.toString();
                String statusString = batchLoader.loadIndividualBatch(logicalNodeConfigurationManager, requestBody, false, this.reportingType);

                if (statusString.equals("SUCCESS")) {
                    String successMessage = String.format(LOAD_SUCCESS_MESSAGE_PATTERN, currentFileOrDirectory.getFileName());
                    logger.info(successMessage);
                } else
                    logger.error(statusString);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }
}