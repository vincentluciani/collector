package writerTest;

import org.junit.Before;
import org.junit.Test;
import reader.LogicalNodeConfigurationManager;
import writter.JSONBodyCreator;

import java.io.IOException;
import java.nio.file.Paths;

import static junit.framework.TestCase.assertEquals;

public class JSONBodyCreatorTest {

    LogicalNodeConfigurationManager logicalNodeConfigurationManager;

    @Before
    public void initialize() throws IOException {
        logicalNodeConfigurationManager = new LogicalNodeConfigurationManager(Paths.get("C:\\test_java\\collector\\src\\test\\testArtifacts"),"us");
    }
    @Test
    public void checkOutputBasedOnInputString(){

        String inputString="3;2b";
        String outputTemplate = logicalNodeConfigurationManager.getOutputCreationTemplate();

        String expectedOutput="[{\"id\": \"1\",\"field0\": \"3\",\"field1\": \"2b\"]";

        JSONBodyCreator jsonBodyCreator = new JSONBodyCreator("1",outputTemplate,inputString);
        assertEquals(expectedOutput,jsonBodyCreator.toString());

    }


}
