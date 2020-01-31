package loaderTest;

import loader.BatchLoader;
import org.junit.Test;
import reader.LogicalNodeConfigurationManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class batchLoaderTest {

    @Test
    public void loadBatchToElastic() throws IOException {

        LogicalNodeConfigurationManager logicalNodeConfigurationManager = new LogicalNodeConfigurationManager(Paths.get("C:\\test_java\\collector\\src\\test\\testArtifacts"),"ar");

        BatchLoader batchLoader = new BatchLoader();
        String requestBody ="{\"index\":{\"_id\":\"a3\"}}\n" +
                "{\"field0\":\"31\",\"field1\":\"32\",\"field2\":\"33\"}\n" +
                "{\"index\":{\"_id\":\"a4\"}}\n" +
                "{\"field0\":\"41\",\"field1\":\"42\",\"field2\":\"43\"}\n";

        String statusString = batchLoader.loadIndividualBatch(logicalNodeConfigurationManager,"batch_000_ar_products",requestBody,false);

        JSONAnswerAnalyzer jsonAnswerAnalyzer = new JSONAnswerAnalyzer(statusString);
        /* check: 3 times
        errors: false
        _index: ar
        _type: products
        result: created or updated
        status:201
         */
        ArrayList<JSONAnswerAnalyzer.ResultContent> resultContents = jsonAnswerAnalyzer.getJSONContent();

        //assertEquals()


/*
{"took":7, "errors": false, "items":[{"index":{"_index":"test","_type":"_doc","_id":"1","_version":1,"result":"created","forced_refresh":false}}]}
 */

    }
}
