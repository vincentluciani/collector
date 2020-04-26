package reader.websiteReader;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;
        import javax.net.ssl.SSLPeerUnverifiedException;
        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.*;
        import java.security.cert.Certificate;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

public class URLReaderWithMatch {

    String urlToRead;
    String protocol;
    boolean isProxy;
    String resultString;

    public URLReaderWithMatch(String urlToRead, String protocol, boolean isProxy) throws MalformedURLException {
        this.urlToRead = urlToRead;
        this.protocol = protocol;
        this.isProxy = isProxy;
        this.connect();
    }

/* https://stackoverflow.com/questions/9619030/resolving-javax-net-ssl-sslhandshakeexception-sun-security-validator-validatore*/
    /*https://access.redhat.com/solutions/43575*/

    private void connect() throws MalformedURLException {

        StringBuilder result = new StringBuilder();
        URL url = new URL(this.urlToRead);
        HttpURLConnection conn;

        try {
            if (this.protocol == "http" && this.isProxy==false ) {
                conn = (HttpURLConnection) url.openConnection();
            } else if (this.protocol == "https" && this.isProxy==false)
            {
                conn = (HttpsURLConnection) url.openConnection();
               // print_https_cert((HttpsURLConnection) conn);
                //dumpl all cert info

            } else if (this.protocol=="http" && this.isProxy==true){
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("gateway.zscaler.net",80));
                conn = (HttpURLConnection) url.openConnection(proxy);
            } else if (this.protocol=="https" && this.isProxy==true){
                System.setProperty("https.proxyHost", "gateway.zscaler.net");
                System.setProperty("https.proxyPort", "80");
                //InputStream in = url.openStream();
                conn = (HttpsURLConnection) url.openConnection();
            }
            else {

                conn = (HttpURLConnection) url.openConnection();
            }

            conn.setRequestMethod("GET");

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line+"\n");
            }
            rd.close();

            this.resultString = result.toString();

        } catch (SSLPeerUnverifiedException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public ArrayList<String> getListOfURLs(String textPattern) throws IOException {

        ArrayList<String> listOfURLs = new ArrayList<String>();

        String result="";

        Pattern pattern = Pattern.compile(textPattern);

        Matcher matcher = pattern.matcher(this.resultString);

        while (matcher.find()) {
            listOfURLs.add(matcher.group(1));
        }

        return listOfURLs;
    }

    private void print_https_cert(HttpsURLConnection con){

        if(con!=null){

            try {

                System.out.println("Response Code : " + con.getResponseCode());
                System.out.println("Cipher Suite : " + con.getCipherSuite());
                System.out.println("\n");

                Certificate[] certs = con.getServerCertificates();
                for(Certificate cert : certs){
                    System.out.println("Cert Type : " + cert.getType());
                    System.out.println("Cert Hash Code : " + cert.hashCode());
                    System.out.println("Cert Public Key Algorithm : "
                            + cert.getPublicKey().getAlgorithm());
                    System.out.println("Cert Public Key Format : "
                            + cert.getPublicKey().getFormat());
                    System.out.println("\n");
                }

            } catch (SSLPeerUnverifiedException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }

        }

    }
}
