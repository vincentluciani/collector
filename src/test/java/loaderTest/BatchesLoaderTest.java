package loaderTest;

import loader.BatchesLoader;
import org.junit.Test;
import manager.LogicalNodeConfigurationManager;

import java.io.IOException;
import java.nio.file.Paths;

import static junit.framework.TestCase.assertEquals;

public class BatchesLoaderTest {

    @Test
    public void areBatchesLoaded() throws IOException {

        LogicalNodeConfigurationManager logicalNodeConfigurationManager = new LogicalNodeConfigurationManager(Paths.get("C:\\test_java\\collector\\src\\test\\testArtifacts"),"ar");

        BatchesLoader batchesLoader = new BatchesLoader(logicalNodeConfigurationManager,"verbose");

        boolean result = batchesLoader.loadAllBatches();

        assertEquals(true,result);

    }

}
