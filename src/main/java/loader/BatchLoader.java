package loader;
/* https://spring.io/guides/gs/consuming-rest/ */

import manager.LogicalNodeConfigurationManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BatchLoader {

    private static final String SUCCESS = "SUCCESS";

    public String loadIndividualBatch(LogicalNodeConfigurationManager logicalNodeConfigurationManager, String requestBody, boolean isProxy, String reportingType) throws IOException {

        HttpURLConnection conn;
        String outputMessage = "";

        String url = "http://localhost:9200/" + logicalNodeConfigurationManager.getDestinationDataPool() + "/" + logicalNodeConfigurationManager.getDestinationSubDataPool() + "/_bulk";
        if (isProxy) {

            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("gateway.zscaler.net", 80));
            conn = (HttpURLConnection) new URL(url).openConnection(proxy);

        } else {
            conn = (HttpURLConnection) new URL(url).openConnection();
        }

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        // to be able to write content to the connection output stream
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            outputMessage = response.toString();

            if ((reportingType.equals("silent")) || (reportingType.isEmpty())) {
                outputMessage = extractErrorMessage(outputMessage);
            }

        }

        return outputMessage;

    }

    public String extractErrorMessage(String outputMessage) {
        Pattern patternFailure = Pattern.compile("\"errors\":true");
        Pattern patternSuccess = Pattern.compile("\"errors\":false");
        Matcher matcherFailure = patternFailure.matcher(outputMessage);
        Matcher matcherSuccess = patternSuccess.matcher(outputMessage);

        if (matcherFailure.find()) {
            return outputMessage;
        } else if (matcherSuccess.find()) {
            return SUCCESS;
        } else {
            return "";
        }

    }
}
