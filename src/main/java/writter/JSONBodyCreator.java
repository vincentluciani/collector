package writter;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONBodyCreator {

    String csvContent;
    String outputTemplate;
    String documentId;
    String actionString;

    public JSONBodyCreator(String documentId, String outputTemplate, String csvContent, String actionString){

        this.csvContent = csvContent;
        this.outputTemplate = outputTemplate;
        this.documentId = documentId;
        this.actionString = actionString;
    }

    public String toString(){

        ArrayList<String> splitted = new ArrayList<String>();

        Pattern pattern = Pattern.compile("_\\$\\{([^}]+)\\}");

        Matcher matcher = pattern.matcher(this.csvContent);

        while (matcher.find()) {
            splitted.add(matcher.group(1));
        }

        String jsonOutput = this.outputTemplate.replace("${id}",this.documentId);
        jsonOutput = jsonOutput.replace("${action}",this.actionString);

        int i=0;
        for ( String element : splitted){

            String replaceFrom = String.format("${%d}",i);
            jsonOutput = jsonOutput.replace(replaceFrom,element);
            i++;
        }

        return jsonOutput;

    }

}
