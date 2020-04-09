/*
 POST /_bulk
 {"_index":nodeId,"_type":"product"}
 ....
 {"delete":{"_index":nodeId,"_type":"product","_id":get from filename}}\n
 {"update":{"_index":nodeId,"_type":"product","_id":get from filename}}\n
 {"column":value}\n

 bulk between 1000 and 5000 docs

 [{"id": "653767","autnReference": "22936326_DA","reference": "22936326_DA","idolTitle": "KSA1000DLC4CF |3606480003530|7514016758|Vinkel 1000A flad m/brt. på mål","keywords": "KSA1000DLC4CF |3606480003530|7514016758|Vinkel 1000A flad m/brt. på mål","openUrl": "","openUrl_original": null,"image": "","contentCategory": "PRODUCT","contentCategoryName": null,"dreDate": "","title": "KSA1000DLC4CF |3606480003530|7514016758|Vinkel 1000A flad m/brt. på mål","highlightedTitle": "KSA1000DLC4CF |3606480003530|7514016758|Vinkel 1000A flad m/brt. på mål"}]

  */

package writter;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import manager.LogicalNodeConfigurationManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BatchCreator {

    private String logicalNode;
    private Path baseDirectory;
    LogicalNodeConfigurationManager logicalNodeConfigurationManager;
    private static Integer batchNumber=0;
    private Collection<Path> currentFilesUsedForBatch = new ArrayList<Path>();

    private static final Logger logger = LogManager.getLogger(BatchCreator.class.getName());

    public BatchCreator(String logicalNode, String baseDirectory, LogicalNodeConfigurationManager logicalNodeConfigurationManager){
        this.logicalNode = logicalNode;
        this.baseDirectory = Paths.get(baseDirectory);
        this.logicalNodeConfigurationManager = logicalNodeConfigurationManager;
    }

    public String putContainOfInputInString(){

        String batchContent = "";
        StringBuilder stringBuilder = new StringBuilder(batchContent);

        Integer lineInBatchCount=1;
        try (DirectoryStream<Path> workingDirectoryStream = Files.newDirectoryStream(this.baseDirectory)) {
            for (Path currentFile : workingDirectoryStream) {
                String filename = currentFile.getFileName().toString();
                List<String> lines  = FileUtils.readLines(currentFile.toFile(), StandardCharsets.UTF_8);
                for (java.lang.String line : lines){
                    JSONBodyCreator jsonBodyCreator = new JSONBodyCreator(filename,
                            this.logicalNodeConfigurationManager.getOutputCreationTemplate(),
                            line,
                            "index");
                    stringBuilder.append(jsonBodyCreator.toString()).append("\n");
                }
                this.currentFilesUsedForBatch.add(currentFile);


                if ( lineInBatchCount >= this.logicalNodeConfigurationManager.getWriterBatchSize()){
                    break;
                }
                lineInBatchCount++;
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }

        return stringBuilder.toString();
    }

    public void toFile() throws IOException {

        String stringToOutput = this.putContainOfInputInString();

        String batchFileName = String.format("batch_%03d_%s_%s", batchNumber,
                logicalNode,logicalNodeConfigurationManager.getDestinationSubDataPool());
        batchNumber++;
        Path batchPath = logicalNodeConfigurationManager.getBatchForUploadBasePath().resolve(batchFileName);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(batchPath.toFile())))
        {
            writer.write( stringToOutput );
        }
        catch ( IOException e)
        {
            logger.error(e.getMessage());
        }

        for (Path currentFileName : this.currentFilesUsedForBatch)
        {
            // Move the file to processed directory
            File targetFileName = logicalNodeConfigurationManager.getBatchForUploadBasePath().resolve("work")
                    .resolve("directoryWithProcessedFiles")
                    .resolve(currentFileName.getFileName())
                    .toFile();

            FileUtils. moveFile(currentFileName.toFile(), targetFileName);
        }

        this.currentFilesUsedForBatch.clear();
    }
}
