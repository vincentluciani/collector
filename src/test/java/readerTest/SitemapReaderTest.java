package readerTest;

import manager.LogicalNodeConfigurationManager;
import org.junit.Test;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import reader.websiteReader.HTMLReader;
import reader.websiteReader.SitemapReader;


import static junit.framework.TestCase.assertEquals;


public class SitemapReaderTest {

    @Test
    public void checkStringExtracted() throws IOException {

        LogicalNodeConfigurationManager logicalNodeConfigurationManager = new LogicalNodeConfigurationManager(Paths.get("C:\\test_java\\collector\\src\\test\\testArtifacts"),"vincent");
        SitemapReader sitemapReader = new SitemapReader(logicalNodeConfigurationManager, "0");
        ArrayList<String> listOfURLs = new ArrayList<String>();


        listOfURLs = sitemapReader.getURLList();

        for ( String url : listOfURLs){
            System.out.println(url);
        }
        //assertEquals("{\"value:\"Rajkumar Hirani\",\"href:\"/wiki/Rajkumar_Hirani\"}",directorName);
    }



}
