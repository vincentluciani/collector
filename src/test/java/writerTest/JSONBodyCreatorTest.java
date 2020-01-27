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

        String inputString="3;2b;8";
        String outputTemplate = logicalNodeConfigurationManager.getOutputCreationTemplate();

        String expectedOutput="{\"update\":{\"_id\":\"1\"}}\n{\"field0\":\"3\",\"field1\":\"2b\",\"field2\":\"8\"}";

        JSONBodyCreator jsonBodyCreator = new JSONBodyCreator("1",outputTemplate,inputString,"update");
        assertEquals(expectedOutput,jsonBodyCreator.toString());

    }


}
