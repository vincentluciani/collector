// https://stackoverflow.com/questions/24772828/how-to-parse-html-table-using-jsoup
package reader.readers.wikiReader;

import lombok.Getter;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class WebPageReader {


    protected Document HTMLDocument;


    public WebPageReader(String url) throws IOException {
        try {
            HTMLDocument = Jsoup.connect(url).get();
        } catch (HttpStatusException ex) {
            System.out.println(ex);
        }

    }

    public String readPage() {
        String resultString = "";

        return resultString;
    }

    public abstract void getValueInLine(Elements elements);

    public abstract void analyzeRowTitleText(String currentRowTitleText, String currentRowValueText);

    public void readInfoTable(String infoBoxClassName) {

        Elements infoboxTables = this.HTMLDocument.getElementsByClass(infoBoxClassName);
        Elements lines = infoboxTables.select("tr");
        List<Entity> extractedInformation = new ArrayList<Entity>();

        for (Element line : lines) {
            Elements currentRowTitle = line.select("th");
            String currentRowTitleText = currentRowTitle.text();
            Elements currentRowValue = line.select("td");

            getValueInLine(currentRowValue);

            Elements subElements = currentRowValue.select("li");

            String currentRowValueText = "";
            String hrefs = "";

            /* Cell */
            if (subElements.size() == 0) {
                Elements insideLine = currentRowValue.select("a");
                hrefs = insideLine.attr("href");
                if (hrefs != "") {
                    currentRowValueText += "{\"value\":\"" + currentRowValue.text() + "\",\"href\":\"" + hrefs + "\"}";
                } else {
                    currentRowValueText += "{\"value\":\"" + currentRowValue.text() + "\"}";
                }

            } else {

                List<Entity> entities = new ArrayList<Entity>();
                currentRowValueText = "[";
                for (Element subline : subElements) {
                    Elements insideSubLine = subline.select("a");
                    currentRowValueText += "{\"value\":\"" + subline.text() + "\",\"href\":\"" + insideSubLine.attr("href") + "\"},";
                    entities.add(new Entity(subline.text(), insideSubLine.attr("href")));
                }
                currentRowValueText += "]";
            }

            analyzeRowTitleText(currentRowTitleText,currentRowValueText);

        }
    }


}
