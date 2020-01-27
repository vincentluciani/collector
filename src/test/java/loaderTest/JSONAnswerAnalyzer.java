package loaderTest;

import dispatcherTest.LogFileAnalyzer;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONAnswerAnalyzer {

   // Pattern pattern = Pattern.compile("(.*) (.*);(.*);.*;(.*)");
    //tood: READ JSON
    //https://howtodoinjava.com/library/json-simple-read-write-json-examples/

    private Path jsonFileLocation;

    public JSONAnswerAnalyzer(Path jsonFileLocation){
        this.jsonFileLocation = jsonFileLocation;
    }

    public ArrayList<JSONAnswerAnalyzer.resultContent> getJSONContent() throws IOException {

        List<String> lines = FileUtils.readLines(jsonFileLocation.toFile(), "UTF-8");
        ArrayList<JSONAnswerAnalyzer.resultContent> statusFileContent = new ArrayList<JSONAnswerAnalyzer.resultContent>();

        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            JSONAnswerAnalyzer.resultContent resultFileCurrentLine = new JSONAnswerAnalyzer.resultContent();
            while (matcher.find()) {
                resultFileCurrentLine.initialize(matcher.group(1),
                        matcher.group(2),
                        matcher.group(3),
                        matcher.group(4));
            }
            if (!resultFileCurrentLine.isEmpty()){
                statusFileContent.add(resultFileCurrentLine);
            }
        }
        return statusFileContent;
    }


    public class resultContent {
        String index;
        String type;
        String result;
        String status;

        public void initialize(String index,String type,String result,String status){
            this.index = index;
            this.type = type;
            this.result = result;
            this.status = status;
        }

        resultContent(){
        }

        public boolean isEmpty(){
            if ( this.index != null && this.type!=null && this.result!=null && this.status!=null){
                return false;
            }
            return true;
        }
    }


}
