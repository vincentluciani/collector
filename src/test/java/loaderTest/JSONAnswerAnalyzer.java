package loaderTest;

import dispatcherTest.LogFileAnalyzer;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

public class JSONAnswerAnalyzer {

    //tood: READ JSON
    //https://howtodoinjava.com/library/json-simple-read-write-json-examples/

    private String jsonToAnalyze;

    public JSONAnswerAnalyzer(String jsonToAnalyze){
        this.jsonToAnalyze = jsonToAnalyze;
    }

    public ArrayList<JSONAnswerAnalyzer.ResultContent> getJSONContent() throws IOException, JSONException {

        ArrayList<JSONAnswerAnalyzer.ResultContent> resultContents = new ArrayList<JSONAnswerAnalyzer.ResultContent>();

        JSONObject jsonObject = new JSONObject(jsonToAnalyze);
        boolean errors = jsonObject.getBoolean("errors");

        JSONArray items = jsonObject.getJSONArray("items");



        int numberOfItems = items.length();

        for (int i=0; i < numberOfItems; i++){
            JSONObject currentItem = items.getJSONObject(i);

            JSONObject insideCurrentItem = currentItem.getJSONObject("index");
            String index = insideCurrentItem.getString("_index");
            String type = insideCurrentItem.getString("_type");
            String id = insideCurrentItem.getString("_id");
            String result = insideCurrentItem.getString("result");

            ResultContent currentResult = new ResultContent(
                    index,
                    type,
                    id,
                    errors,
                    result
                        );
            resultContents.add(currentResult);
        }

        /* Real:
{"took":700,"errors":false,"items":[{"index":{"_index":"ar","_type":"products","_id":"a3","_version":1,"result":"created","_shards":{"total":2,"successful":1,"failed":0},"_seq_no":0,"_primary_term":1,"status":201}},{"index":{"_index":"ar","_type":"products","_id":"a4","_version":1,"result":"created","_shards":{"total":2,"successful":1,"failed":0},"_seq_no":1,"_primary_term":1,"status":201}}]}
 */

/*
{"took":7, "errors": false, "items":[{"index":{"_index":"test","_type":"_doc","_id":"1","_version":1,"result":"created","forced_refresh":false}}]}
 */

        return resultContents;
    }


    public class ResultContent {
        String index;
        String type;
        String id;
        boolean errors;
        String result;

        public ResultContent(String index,String type,String id,boolean errors,String result){
            this.index = index;
            this.type = type;
            this.errors = errors;
            this.id = id;
            this.result = result;
        }

    public boolean isEmpty(){
            if ( this.index != null && this.type!=null && this.id!=null){
                return false;
            }
            return true;
        }
    }


}
