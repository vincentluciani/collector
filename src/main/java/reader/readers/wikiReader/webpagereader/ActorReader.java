package reader.readers.wikiReader.webpagereader;

import lombok.Getter;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import reader.readers.wikiReader.WebPageReader;

import java.io.IOException;

public class ActorReader extends WebPageReader {

    private static final String BORN = "born";
    private static final String YEARS_ACTIVE = "years active";
    private static final String SPOUSES = "spouse(s)";
    private static final String CHILDREN = "children";
    private static final String HONOURS = "honours";
    private static final String POSTER = "poster";
    private static String EARLY_LIFE = "early life and family";

    @Getter private String born;
    @Getter private String yearsActive;
    @Getter
    private String spouses;
    @Getter
    private String children;
    @Getter
    private String honours;
    @Getter
    private String poster;
    @Getter
    private String earlyLife;


    public ActorReader(String url) throws IOException {
        super(url);
        readInfoTable("infobox biography vcard");
        readEarlyLife();
    }
    public void getValueInLine(Elements currentRowValue){
        if (currentRowValue.text().indexOf(POSTER) >= 0) {
            Elements hyperlinks = currentRowValue.select("a");
            Elements images = hyperlinks.select("img");
            poster = "https:" + images.attr("src");

            System.out.println("Poster:" + poster);
        }
    }

    public void analyzeRowTitleText(String currentRowTitleText, String currentRowValueText){
        switch (currentRowTitleText.trim().toLowerCase()) {
            case BORN:
                born = currentRowValueText;
                System.out.println("Found born:" + born);
                break;
            case YEARS_ACTIVE:
                yearsActive = currentRowValueText;
                System.out.println("Found years active:" + yearsActive);
                break;
            case SPOUSES:
                spouses = currentRowValueText;
                System.out.println("Found spouses:" + spouses);
                break;
            case CHILDREN:
                children = currentRowValueText;
                System.out.println("Found children:" + children);
                break;
            case HONOURS:
                honours = currentRowValueText;
                System.out.println("Found honours:" + honours);
                break;
        }



    }

// TODO GENERALISE
    public void readEarlyLife() {

        Elements mainSections = this.HTMLDocument.select("h2");

        for (Element currentSection : mainSections) {
            String sectionTitle = currentSection.text();
            System.out.println("section title:" + sectionTitle);

            String cleanSectionTitle = sectionTitle.trim().toLowerCase();

            if (cleanSectionTitle.indexOf(EARLY_LIFE) >= 0) {
                System.out.println("Found early life chapter:" + sectionTitle);

                this.earlyLife = "";
                String elementTag = "";
                Element nextSibling = currentSection.nextElementSibling();

                do {
                    this.earlyLife += nextSibling.text();
                    //System.out.println("plot:" + this.plotText);
                    nextSibling = nextSibling.nextElementSibling();
                    elementTag = nextSibling.tag().toString();
                } while (elementTag == "p");

                System.out.println("Early life:" + this.earlyLife);
            }
        }

    }
}
