package loaderTest;

import loader.BatchLoader;
import org.junit.Assert;
import org.junit.Test;
import manager.LogicalNodeConfigurationManager;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class BatchLoaderTest {

    @Test
    public void loadBatchToElastic() throws IOException {

        LogicalNodeConfigurationManager logicalNodeConfigurationManager = new LogicalNodeConfigurationManager(Paths.get("C:\\test_java\\collector\\src\\test\\testArtifacts"),"ar");

        BatchLoader batchLoader = new BatchLoader();
        String requestBody ="{\"index\":{\"_id\":\"a3\"}}\n" +
                "{\"field0\":\"31\",\"field1\":\"32\",\"field2\":\"33\"}\n" +
                "{\"index\":{\"_id\":\"a4\"}}\n" +
                "{\"field0\":\"41\",\"field1\":\"42\",\"field2\":\"43\"}\n";

        String statusString = batchLoader.loadIndividualBatch(logicalNodeConfigurationManager,requestBody,false,"verbose");


        JSONAnswerAnalyzer jsonAnswerAnalyzer = new JSONAnswerAnalyzer(statusString);
        /* check: 3 times
        errors: false
        _index: ar
        _type: products
        result: created or updated
        status:201
         */
        ArrayList<JSONAnswerAnalyzer.ResultContent> resultContents = jsonAnswerAnalyzer.getJSONContent();

        String firstIndex = resultContents.get(0).index;
        String firstType = resultContents.get(0).type;
        String firstId= resultContents.get(0).id;
        boolean firstError= resultContents.get(0).errors;
        String firstResult = resultContents.get(0).result;

        assertEquals("ar",firstIndex);
        assertEquals("es",firstType);

        Set<String> firstErrorPossibleAnswers = new HashSet<>();
        firstErrorPossibleAnswers.add("created");
        firstErrorPossibleAnswers.add("updated");
        Assert.assertTrue(firstErrorPossibleAnswers.contains(firstResult));
        assertEquals(false,firstError);

        String secondIndex = resultContents.get(1).index;
        String secondType = resultContents.get(1).type;
        String secondId= resultContents.get(1).id;
        boolean secondError= resultContents.get(1).errors;
        String secondResult = resultContents.get(0).result;

        assertEquals("ar",secondIndex);
        assertEquals("es",secondType);

        Assert.assertTrue(firstErrorPossibleAnswers.contains(secondResult));
        assertEquals(false,secondError);


/*
{"took":7, "errors": false, "items":[{"index":{"_index":"test","_type":"_doc","_id":"1","_version":1,"result":"created","forced_refresh":false}}]}
 */

/* Real:
{"took":700,"errors":false,"items":[{"index":{"_index":"ar","_type":"products","_id":"a3","_version":1,"result":"created","_shards":{"total":2,"successful":1,"failed":0},"_seq_no":0,"_primary_term":1,"status":201}},{"index":{"_index":"ar","_type":"products","_id":"a4","_version":1,"result":"created","_shards":{"total":2,"successful":1,"failed":0},"_seq_no":1,"_primary_term":1,"status":201}}]}
 */
    }
}
