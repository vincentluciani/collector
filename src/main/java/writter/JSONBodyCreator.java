package writter;

import org.apache.commons.io.FileUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class JSONBodyCreator {

    String csvContent;
    String outputTemplate;
    String documentId;

    public JSONBodyCreator(String documentId, String outputTemplate, String csvContent){

        this.csvContent = csvContent;
        this.outputTemplate = outputTemplate;
        this.documentId = documentId;
    }

    public String toString(){

        String[] splitted = this.csvContent.split(";");
        String jsonOutput = this.outputTemplate.replace("${id}",this.documentId);

        for ( int i=0; i < splitted.length;i++){

            String replaceFrom = String.format("${%d}",i);
            jsonOutput = jsonOutput.replace(replaceFrom,splitted[i]);
        }

        return jsonOutput;

    }

}
