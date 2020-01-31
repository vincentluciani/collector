package loader;
/* https://spring.io/guides/gs/consuming-rest/ */

import reader.LogicalNodeConfigurationManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;

public class BatchLoader {

    public BatchLoader(){

    }

    public String loadIndividualBatch(LogicalNodeConfigurationManager logicalNodeConfigurationManager,String batchName, String requestBody, boolean isProxy) throws IOException {

        //https://www.baeldung.com/httpurlconnection-post

        HttpURLConnection conn;
        String outputMessage="";

        String url = "https://localhost:9200/"+ logicalNodeConfigurationManager.getLogicalNodeID() + "/"+ logicalNodeConfigurationManager.getDestinationDataPool()+"/_bulk";
        if (isProxy){

            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("gateway.zscaler.net",80));
            conn = (HttpURLConnection) new URL(url).openConnection(proxy);

        } else {
            conn = (HttpURLConnection) new URL(url).openConnection();
        }

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type","application/json; utf-8");
        conn.setRequestProperty("Accept","application/json");
        // to be able to write content to the connection output stream
        conn.setDoOutput(true);

        try(OutputStream os = conn.getOutputStream()) {
            byte[] input = requestBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        InputStreamReader inputStreamReader = new InputStreamReader((conn.getInputStream()));

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            outputMessage=response.toString();
        }

        return outputMessage;

    }

}
