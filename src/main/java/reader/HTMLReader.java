// https://stackoverflow.com/questions/24772828/how-to-parse-html-table-using-jsoup
package reader;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HTMLReader {

    Document HTMLDocument;

    private String directorName;
    private String plotText;

    private static final String DIRECTED_BY = "directed by";

    private static final String PLOT = "plot";

    public HTMLReader(String url) throws IOException {
        try {
            HTMLDocument = Jsoup.connect(url).get();
        } catch (HttpStatusException ex) {
            System.out.println(ex);
        }
        readPlot();
        readInfoTable();
    }
    public String readPage(){
        String resultString="";

        return resultString;
    }

    public void readPlot(){

        Elements mainSections = this.HTMLDocument.select("h2");

        for (Element currentSection : mainSections) {
            String sectionTitle = currentSection.text();
            System.out.println("section title:"+sectionTitle);

            switch ( sectionTitle.trim().toLowerCase() )
            {
                case PLOT:
                    System.out.println("Found plot chapter:" + sectionTitle);

                    this.plotText = "";
                    String elementTag = "";
                    Element nextSibling = currentSection.nextElementSibling();

                    do {
                        this.plotText += nextSibling.text();
                        System.out.println("plot:" + this.plotText);
                        nextSibling = nextSibling.nextElementSibling();
                        elementTag = nextSibling.tag().toString();
                    } while ( elementTag == "p" );

                    System.out.println("plot text:"+this.plotText);
            }
        }

    }
    public void readInfoTable(){

        Elements infoboxTables = this.HTMLDocument.getElementsByClass("infobox vevent");
        Elements lines = infoboxTables.select("tr");

        for (Element line : lines) {
            Elements currentRowTitle = line.select("th");
            String currentRowTitleText = currentRowTitle.text();
            Elements currentRowValue = line.select("td");
            String currentRowValueText = currentRowValue.text();

            System.out.println("row title:" + currentRowTitleText + " row value:"+ currentRowValueText);

            switch ( currentRowTitleText.trim().toLowerCase() )
            {
                case DIRECTED_BY:
                    directorName = currentRowValueText;
                    System.out.println("Found director:" + directorName);
            }

        }
    }

    public String getDirectorName(){
        return this.directorName;
    }
}
