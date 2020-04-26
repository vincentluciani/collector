package writerTest;

import org.junit.Before;
import org.junit.Test;
import manager.LogicalNodeConfigurationManager;
import writter.BatchCreator;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static junit.framework.TestCase.assertEquals;

public class BatchCreatorTest
{
    LogicalNodeConfigurationManager logicalNodeConfigurationManager;

    @Before
    public void initialize() throws IOException {
        logicalNodeConfigurationManager = new LogicalNodeConfigurationManager(Paths.get("C:\\test_java\\collector\\src\\test\\testArtifacts"),"us");
    }

    @Test
    public void checkBatchIsCreated() throws IOException {

        /*
        Files are copied from 09_batch_creation\work\directoryWithFilesToUpdate
        to /src/test/testArtifacts/batch_creation/work/directoryWithFilesToUpdate
        by maven

        Then what we expect is that the application, from the 5 files
         present in the directory, first creates a batch containing 4 entries,
         then a batch containing one entry.

        Before starting the test, the output, batches_to_upload\work\directoryWithProcessedFiles\,
        is cleaned by maven.
         */
        String baseDirectory = "C:\\test_java\\collector\\src\\test\\testArtifacts\\batch_creation\\work"
                                + "\\directoryWithFilesToUpdate";

        BatchCreator batchCreator = new BatchCreator("us",baseDirectory,logicalNodeConfigurationManager);

       String expectedContent =
               "{\"index\":{\"_id\":\"a3\"}}\n"+
               "{\"field0\":\"31\",\"field1\":\"32\",\"field2\":\"33\"}\n" +
               "{\"index\":{\"_id\":\"a4\"}}\n"+
               "{\"field0\":\"41\",\"field1\":\"42\",\"field2\":\"43\"}\n";

        // TODO SHOULD BE index and data underneath and also _id
        // so I take off the 'update' "_index" : "test" and "_type" is inside url
        // POST  ..../indexname/typename/_bulk
        //{ "index" : {  "_id" : "1" } }
        //{ "field1" : "value1" }

        batchCreator.toFile();

        Path batchPath = Paths.get("C:\\test_java\\collector\\src\\test\\testArtifacts\\batches_to_upload\\batch_000_us_en");
        String batchContent;
        batchContent = readFile(batchPath.toString(), StandardCharsets.UTF_8);
        assertEquals(expectedContent,batchContent);


      String expectedContent2 =
              "{\"index\":{\"_id\":\"a5\"}}\n"+
                      "{\"field0\":\"51\",\"field1\":\"52\",\"field2\":\"53\"}\n" +
                      "{\"index\":{\"_id\":\"a6\"}}\n"+
                      "{\"field0\":\"61\",\"field1\":\"62\",\"field2\":\"63\"}\n";


        batchCreator.toFile();

        Path batchPath2 = Paths.get("C:\\test_java\\collector\\src\\test\\testArtifacts\\batches_to_upload\\batch_001_us_en");
        String batchContent2 = readFile(batchPath2.toString(), StandardCharsets.UTF_8);
        assertEquals(expectedContent2,batchContent2);


        String expectedContent3 =
                "{\"index\":{\"_id\":\"a7\"}}\n"+
                        "{\"field0\":\"71\",\"field1\":\"72\",\"field2\":\"73\"}\n";


        batchCreator.toFile();

        Path batchPath3 = Paths.get("C:\\test_java\\collector\\src\\test\\testArtifacts\\batches_to_upload\\batch_002_us_en");
        String batchContent3 = readFile(batchPath3.toString(), StandardCharsets.UTF_8);

        assertEquals(expectedContent3,batchContent3);

    }

    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
// POST _bulk
