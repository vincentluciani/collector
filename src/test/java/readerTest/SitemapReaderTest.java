package readerTest;

import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;

import reader.websiteReader.HTMLReader;
import reader.websiteReader.SitemapReader;


import static junit.framework.TestCase.assertEquals;


public class SitemapReaderTest {

    @Test
    public void checkStringExtracted() throws IOException {
        SitemapReader sitemapReader = new SitemapReader("https://www.vincent-luciani.com/sitemap.xml", "<loc>(.*tutoria.*)</loc>","https",false);
        ArrayList<String> listOfURLs = new ArrayList<String>();


        listOfURLs = sitemapReader.getURLList();

        for ( String url : listOfURLs){
            System.out.println(url);
        }
        //assertEquals("{\"value:\"Rajkumar Hirani\",\"href:\"/wiki/Rajkumar_Hirani\"}",directorName);
    }



}
