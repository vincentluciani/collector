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
        JSONArray items = jsonObject.getJSONArray("items");
        int numberOfItems = items.length();

        for (int i=0; i > numberOfItems; i++){
            JSONObject currentItem = items.getJSONObject(i);
            ResultContent currentResult = new ResultContent(
                            currentItem.getString("_index"),
                            currentItem.getString("_type"),
                            currentItem.getString("_id"),
                            currentItem.getString("result")
                        );
            resultContents.add(currentResult);
        }

/*
{"took":7, "errors": false, "items":[{"index":{"_index":"test","_type":"_doc","_id":"1","_version":1,"result":"created","forced_refresh":false}}]}
 */

        return resultContents;
    }


    public class ResultContent {
        String index;
        String type;
        String result;
        String status;

        public ResultContent(String index,String type,String result,String status){
            this.index = index;
            this.type = type;
            this.result = result;
            this.status = status;
        }

    public boolean isEmpty(){
            if ( this.index != null && this.type!=null && this.result!=null && this.status!=null){
                return false;
            }
            return true;
        }
    }


}
