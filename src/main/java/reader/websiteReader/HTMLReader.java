package reader.websiteReader;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLReader {

    Document HTMLDocument;
    String url;
    String category;

    public HTMLReader(String url) throws IOException {
        try {
            HTMLDocument = Jsoup.connect(url).get();
        } catch (HttpStatusException ex) {
            System.out.println(ex);
        }
        this.url = url;
        findCategoryFromUrl();
    }

    public void findCategoryFromUrl() throws MalformedURLException {

        String path = new URL(url).getPath();

        // todo put in array and parameterize
        Pattern pattern1 = Pattern.compile("^/sql-tutorial/.*");
        Pattern pattern2 = Pattern.compile("^/php-tutorial/.*");

        Matcher matcher1 = pattern1.matcher(path);
        Matcher matcher2 = pattern2.matcher(path);

        if (matcher1.find()) {
            this.category = "SQL";
        } else if (matcher2.find()){
            this.category ="PHP";
        } else {
            this.category = "other";
        }

    }
    public ArrayList<Entity> readKnowledgeTables()
    {
        Elements mainSections = this.HTMLDocument.select("h2");
        ArrayList<Entity> finalListOfQuestionAnswers = new ArrayList<Entity>();

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
            ArrayList<Entity> currentListOfQuestionAnswers = new ArrayList<Entity>();
                // System.out.println("table:"+elementTag+" element:"+nextSibling.html());
                currentListOfQuestionAnswers = readKnowledgeTable(nextSibling,sectionTitle);

            finalListOfQuestionAnswers.addAll(currentListOfQuestionAnswers);
        }

        return finalListOfQuestionAnswers;
    }

    public ArrayList<Entity> readKnowledgeTable(Element tableElement,String sectionTitle) {

        ArrayList<Entity> listOfQuestionAnswers = new ArrayList<Entity>();

        Elements lines = tableElement.select("tr");

        for (Element line : lines) {

            Elements currentRowValue = line.select("td");
            String question = currentRowValue.get(0).text();
            String answer = currentRowValue.get(1).text();
            System.out.println("question:" + question + " answer:" + answer);

            Entity entity = new Entity(this.category, sectionTitle, question, answer);

            // todo Double quote is replaced with \"

        }
        return listOfQuestionAnswers;
    }
}
