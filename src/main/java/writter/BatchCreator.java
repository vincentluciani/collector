package writter;

import org.apache.commons.io.FileUtils;
import reader.LogicalNodeConfigurationManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class BatchCreator {

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

    public String logicalNode;
    public Path baseDirectory;
    LogicalNodeConfigurationManager logicalNodeConfigurationManager;
    public static Integer batchNumber=0;

    public BatchCreator(String logicalNode, String baseDirectory, LogicalNodeConfigurationManager logicalNodeConfigurationManager){
        this.logicalNode = logicalNode;
        this.baseDirectory = Paths.get(baseDirectory);
        this.logicalNodeConfigurationManager = logicalNodeConfigurationManager;
    }

    public String toString(){

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
                // Move the file to processed directory
                FileUtils.moveFile(currentFile.toFile(),logicalNodeConfigurationManager.getBatchForUploadBasePath()
                        .resolve("work")
                        .resolve("directoryWithProcessedFiles")
                        .resolve(currentFile.getFileName())
                        .toFile());

                if ( lineInBatchCount >= this.logicalNodeConfigurationManager.getWriterBatchSize()){
                    break;
                }
                lineInBatchCount++;
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        return stringBuilder.toString();
    }

    public void toFile(){

        String stringToOutput = this.toString();

        BufferedWriter writer = null;

        String batchFileName = String.format("batch_%03d_%s_%s", batchNumber,
                logicalNode,logicalNodeConfigurationManager.getDestinationDataPool());

        batchNumber++;
        Path batchPath = logicalNodeConfigurationManager.getBatchForUploadBasePath().resolve(batchFileName);

        try
        {
            writer = new BufferedWriter( new FileWriter( batchPath.toFile() ));
            writer.write( stringToOutput );

        }
        catch ( IOException e)
        {
        }
        finally
        {
            try
            {
                if ( writer != null)
                    writer.close( );
            }
            catch ( IOException e)
            {
            }
        }

    }
}
