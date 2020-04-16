package reader.websiteReader;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class SitemapReader {

    Document HTMLDocument;

    public SitemapReader(String sitemapURL) throws IOException {
        try {
            HTMLDocument = Jsoup.connect(sitemapURL).get();
        } catch (HttpStatusException ex) {
            System.out.println(ex);
        }
    }

    public void readSitemap(){
        
    }
}
