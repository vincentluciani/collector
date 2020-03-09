package writter;

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

        String[] splitted = this.csvContent.split(";");
        String jsonOutput = this.outputTemplate.replace("${id}",this.documentId);
        jsonOutput = jsonOutput.replace("${action}",this.actionString);

        for ( int i=0; i < splitted.length;i++){

            String replaceFrom = String.format("${%d}",i);
            jsonOutput = jsonOutput.replace(replaceFrom,splitted[i]);
        }

        return jsonOutput;

    }

}
