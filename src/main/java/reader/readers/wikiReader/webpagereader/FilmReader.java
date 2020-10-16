package reader.readers.wikiReader.webpagereader;

import lombok.Getter;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import reader.readers.wikiReader.WebPageReader;

import java.io.IOException;

public class FilmReader extends WebPageReader {

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
    private static final String POSTER = "poster";
    private static final String PLOT = "plot";

    @Getter private String plotText;
    @Getter private String directedBy;
    @Getter
    public String producedBy;
    @Getter
    public String writtenBy;
    @Getter
    public String screenplayBy;
    @Getter
    public String storyBy;
    @Getter
    public String starring;
    @Getter
    public String musicBy;
    @Getter
    public String cinematography;
    @Getter
    public String editedBy;
    @Getter
    public String productionCompany;
    @Getter
    public String distributedBy;
    @Getter
    public String releaseDate;
    @Getter
    public String runningTime;
    @Getter
    public String country;
    @Getter
    public String language;
    @Getter
    public String budget;
    @Getter
    public String boxOffice;
    @Getter
    public String poster;

    public FilmReader(String url) throws IOException {
        super(url);

        readPlot();
        readInfoTable("infobox vevent");

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


    public void readPlot() {

        Elements mainSections = this.HTMLDocument.select("h2");

        for (Element currentSection : mainSections) {
            String sectionTitle = currentSection.text();
            System.out.println("section title:" + sectionTitle);

            String cleanSectionTitle = sectionTitle.trim().toLowerCase();

            if (cleanSectionTitle.indexOf(PLOT) >= 0) {
                System.out.println("Found plot chapter:" + sectionTitle);

                this.plotText = "";
                String elementTag = "";
                Element nextSibling = currentSection.nextElementSibling();

                do {
                    this.plotText += nextSibling.text();
                    //System.out.println("plot:" + this.plotText);
                    nextSibling = nextSibling.nextElementSibling();
                    elementTag = nextSibling.tag().toString();
                } while (elementTag == "p");

                System.out.println("plot text:" + this.plotText);
            }
        }

    }
}
