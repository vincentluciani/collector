package reader.websiteReader;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import reader.wikiReader.Entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HTMLReader {

    Document HTMLDocument;

    public HTMLReader(String url) throws IOException {
        try {
            HTMLDocument = Jsoup.connect(url).get();
        } catch (HttpStatusException ex) {
            System.out.println(ex);
        }
    }

    public void readKnowledgeTables(){
        Elements mainSections = this.HTMLDocument.select("h2");

        for (Element currentSection : mainSections)
        {
            String sectionTitle = currentSection.text();
            System.out.println("section title:"+sectionTitle);


            Element nextSibling = currentSection.nextElementSibling();
            String elementTag = nextSibling.tag().toString();

            while ( elementTag != "table") {
               nextSibling = nextSibling.nextElementSibling();
               elementTag = nextSibling.tag().toString();
            }

                // System.out.println("table:"+elementTag+" element:"+nextSibling.html());
                readKnowledgeTable(nextSibling);
        }
    }

    public void readKnowledgeTable(Element tableElement){

        Elements lines = tableElement.select("tr");

        for (Element line : lines) {

            Elements currentRowValue = line.select("td");
            String question = currentRowValue.get(0).text();
            String answer = currentRowValue.get(1).text();
            System.out.println("question:"+question+" answer:"+ answer);

    // todo Double quote is replaced with \"
        }

    }

}
