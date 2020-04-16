// https://stackoverflow.com/questions/24772828/how-to-parse-html-table-using-jsoup
package reader.wikiReader;

import lombok.Getter;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebPageReader {

    Document HTMLDocument;

    @Getter
    private String plotText;
    private String directedBy;
    private String producedBy;
    private String writtenBy;
    private String screenplayBy;
    private String storyBy;
    private String starring;
    private String musicBy;
    private String cinematography;
    private String editedBy;
    private String productionCompany;
    private String distributedBy;
    private String releaseDate;
    private String runningTime;
    private String country;
    private String language;
    private String budget;
    private String boxOffice;
    private String poster;

    private static final String DIRECTED_BY = "directed by";
    private static final String PRODUCED_BY = "produced by";
    private static final String WRITTEN_BY = "written by";
    private static final String SCREENPLAY_BY = "screenplay by";
    private static final String STORY_BY = "story by";
    private static final String STARRING = "starring";
    private static final String MUSIC_BY = "music by";
    private static final String CINEMATOGRAPHY = "cinematography";
    private static final String EDITED_BY = "edited by";
    private static final String PRODUCTION_COMPANY = "production company";
    private static final String DISTRIBUTED_BY = "distributed by";
    private static final String RELEASE_DATE = "release date";
    private static final String RUNNING_TIME = "running time";
    private static final String COUNTRY = "country";
    private static final String LANGUAGE = "language";
    private static final String BUDGET = "budget";
    private static final String BOX_OFFICE = "box office";
    private static final String POSTER ="poster";

    private static final String PLOT = "plot";

    public WebPageReader(String url) throws IOException {
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

            String cleanSectionTitle = sectionTitle.trim().toLowerCase();

            if ( cleanSectionTitle.indexOf(PLOT) >= 0){
                    System.out.println("Found plot chapter:" + sectionTitle);

                    this.plotText = "";
                    String elementTag = "";
                    Element nextSibling = currentSection.nextElementSibling();

                    do {
                        this.plotText += nextSibling.text();
                        //System.out.println("plot:" + this.plotText);
                        nextSibling = nextSibling.nextElementSibling();
                        elementTag = nextSibling.tag().toString();
                    } while ( elementTag == "p" );

                    System.out.println("plot text:"+this.plotText);
            }
        }

    }

    // todo cast
    // todo awards

    public void readInfoTable(){

        Elements infoboxTables = this.HTMLDocument.getElementsByClass("infobox vevent");
        Elements lines = infoboxTables.select("tr");

        for (Element line : lines) {
            Elements currentRowTitle = line.select("th");
            String currentRowTitleText = currentRowTitle.text();
            Elements currentRowValue = line.select("td");

            if ( currentRowValue.text().indexOf(POSTER)>=0){
                Elements hyperlinks = currentRowValue.select("a");
                Elements images = hyperlinks.select("img");
                poster = "https:"+images.attr("src");

                System.out.println("Poster:" + poster);
            }

            Elements subElements = currentRowValue.select("li");

            String currentRowValueText = "";
            String hrefs="";

            if ( subElements.size() == 0 ) {
                Elements insideLine = currentRowValue.select("a");
                hrefs = insideLine.attr("href");
                if (hrefs != "") {
                    currentRowValueText+="{\"value:\""+ currentRowValue.text()+"\",\"href:\""+hrefs+"\"}";
                } else {
                    currentRowValueText+="{\"value:\""+ currentRowValue.text()+"\"}";
                }

            } else {

                List<Entity> entities = new ArrayList<Entity>();
                currentRowValueText = "[";
                for (Element subline : subElements){
                    Elements insideSubLine = subline.select("a");
                    currentRowValueText+="{\"value:\""+ subline.text()+"\",\"href:\""+insideSubLine.attr("href")+"\"},";
                    entities.add(new Entity(subline.text(),insideSubLine.attr("href")));
                }
                currentRowValueText+="]";
            }


            // toto theatrical poster

           // System.out.println("row title:" + currentRowTitleText + ",row value:"+ currentRowValueText);

            switch ( currentRowTitleText.trim().toLowerCase() )
            {
                case DIRECTED_BY:
                    directedBy = currentRowValueText;
                    System.out.println("Found directedBy:" + directedBy);
                    break;
                case PRODUCED_BY:
                    producedBy = currentRowValueText;
                    System.out.println("Found producedBy:" + producedBy);
                    break;
                case WRITTEN_BY:
                    writtenBy = currentRowValueText;
                    System.out.println("Found writtenBy:" + writtenBy);
                    break;
                case SCREENPLAY_BY:
                    screenplayBy = currentRowValueText;
                    System.out.println("Found screenplayBy:" + screenplayBy);
                    break;
                case STORY_BY:
                    storyBy = currentRowValueText;
                    System.out.println("Found storyBy:" + storyBy);
                    break;
                case STARRING:
                    starring = currentRowValueText;
                    System.out.println("Found starring:" + starring);
                    break;
                case MUSIC_BY:
                    musicBy = currentRowValueText;
                    System.out.println("Found musicBy:" + musicBy);
                    break;
                case CINEMATOGRAPHY:
                    cinematography = currentRowValueText;
                    System.out.println("Found cinematography:" + cinematography);
                    break;
                case EDITED_BY:
                    editedBy = currentRowValueText;
                    System.out.println("Found editedBy:" + editedBy);
                    break;
                case PRODUCTION_COMPANY:
                    productionCompany = currentRowValueText;
                    System.out.println("Found productionCompany:" + productionCompany);
                    break;
                case DISTRIBUTED_BY:
                    distributedBy = currentRowValueText;
                    System.out.println("Found distributedBy:" + distributedBy);
                    break;
                case RELEASE_DATE:
                    releaseDate = currentRowValueText;
                    System.out.println("Found releaseDate:" + releaseDate);
                    break;
                case RUNNING_TIME:
                    runningTime = currentRowValueText;
                    System.out.println("Found runningTime:" + runningTime);
                    break;
                case COUNTRY:
                    country = currentRowValueText;
                    System.out.println("Found country:" + country);
                    break;
                case LANGUAGE:
                    language = currentRowValueText;
                    System.out.println("Found language:" + language);
                    break;
                case BUDGET:
                    budget = currentRowValueText;
                    System.out.println("Found budget:" + budget);
                    break;
                case BOX_OFFICE:
                    boxOffice = currentRowValueText;
                    System.out.println("Found boxOffice:" + boxOffice);
                    break;
            }



        }
    }

    public String getDirectedBy(){
        return this.directedBy;
    }
}
