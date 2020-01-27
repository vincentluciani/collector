package loaderTest;

import org.junit.Test;
import reader.LogicalNodeConfigurationManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class batchLoaderTest {

    @Test
    public void loadBatchToElastic() throws IOException {

        LogicalNodeConfigurationManager logicalNodeConfigurationManager = new LogicalNodeConfigurationManager(Paths.get("C:\\test_java\\collector\\src\\test\\testArtifacts"),"ar");

        BatchLoader batchLoader = new BatchLoader();

        batchLoader.loadIndividualBatch("batch_000_ar_products");

        Path statusFilePath = batchLoader.getBatchUploadStatusFilesDirectory().resolve("batch_000_ar_products.status");
        JSONAnswerAnalyzer jsonAnswerAnalyzer = new JSONAnswerAnalyzer(statusFilePath);
        /* check: 3 times
        errors: false
        _index: ar
        _type: products
        result: created or updated
        status:201
         */

/*
{"took":7, "errors": false, "items":[{"index":{"_index":"test","_type":"_doc","_id":"1","_version":1,"result":"created","forced_refresh":false}}]}
 */

    }
}
