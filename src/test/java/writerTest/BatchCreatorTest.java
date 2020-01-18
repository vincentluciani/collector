package writerTest;

import org.junit.Before;
import org.junit.Test;
import reader.LogicalNodeConfigurationManager;
import writter.BatchCreator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;

public class BatchCreatorTest
{
    LogicalNodeConfigurationManager logicalNodeConfigurationManager;

    @Before
    public void initialize() throws IOException {
        logicalNodeConfigurationManager = new LogicalNodeConfigurationManager(Paths.get("C:\\test_java\\collector\\src\\test\\testArtifacts"),"us");
    }

    @Test
    public void checkBatchIsCreated(){

        String baseDirectory = "C:\\test_java\\collector\\src\\test\\testArtifacts\\09_batch_creation\\work"
                                + "\\directoryWithNewFiles";

        BatchCreator batchCreator = new BatchCreator("us",baseDirectory,logicalNodeConfigurationManager);

        String batchContent = batchCreator.toString();

        String expectedContent =
                "{\"_index\":\"us\",\"_type\":\"products\"}\n"+
                "{\"update\":{\"id\": \"a1\",\"field0\": \"1\",\"field1\": \"12\",\"field2\": \"13\"}}\n"
                + "{\"update\":{\"id\": \"a2\",\"field0\": \"2\",\"field1\": \"21\",\"field2\": \"22\"}}\n"
                + "{\"update\":{\"id\": \"a3\",\"field0\": \"3\",\"field1\": \"31\",\"field2\": \"32\"}}\n";

        assertEquals(expectedContent,batchContent);
    }
}
// POST _bulk
