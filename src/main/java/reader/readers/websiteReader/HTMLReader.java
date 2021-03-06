package reader.readers.websiteReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLReader {

    private static final Logger logger = LogManager.getLogger(HTMLReader.class.getName());
    Document HTMLDocument;
    String url;
    String category;
    String categoryPattern;
    boolean isUrlReachable = false;

    public HTMLReader(String url, String categoryPattern) throws IOException {
        try {
            HTMLDocument = Jsoup.connect(url).get();
            this.isUrlReachable = true;
        } catch (HttpStatusException ex) {
            this.isUrlReachable = false;
            System.out.println(ex);
        }
        this.url = url;
        this.categoryPattern = categoryPattern;
        logger.info("url:" + this.url);
        findCategoryFromUrl();
    }

    public void findCategoryFromUrl() throws MalformedURLException {

        if (!this.isUrlReachable) {
            this.category = "";
        }
        String path = new URL(url).getPath();

        // todo put in array and parameterize
        Pattern pattern = Pattern.compile(this.categoryPattern);

        Matcher matcher = pattern.matcher(path);

        if (matcher.find()) {
            this.category = matcher.group(1).toUpperCase();
        } else {
            this.category = "other";
        }

        logger.info("category:" + this.category);

    }

    public ArrayList<Entity> readKnowledgeTables() {
        ArrayList<Entity> finalListOfQuestionAnswers = new ArrayList<Entity>();

        if (!this.isUrlReachable) {
            return finalListOfQuestionAnswers;
        }

        Elements mainSections = this.HTMLDocument.select("h2");

        for (Element currentSection : mainSections) {
            String sectionTitle = currentSection.text();

            Element nextSibling = currentSection.nextElementSibling();
            String elementTag = nextSibling.tag().toString();

            while (elementTag != "table") {
                nextSibling = nextSibling.nextElementSibling();
                elementTag = nextSibling.tag().toString();
            }
            ArrayList<Entity> currentListOfQuestionAnswers = new ArrayList<Entity>();
            currentListOfQuestionAnswers = readKnowledgeTable(nextSibling, sectionTitle);

            finalListOfQuestionAnswers.addAll(currentListOfQuestionAnswers);
        }

        return finalListOfQuestionAnswers;
    }

    public ArrayList<Entity> readKnowledgeTable(Element tableElement, String sectionTitle) {

        ArrayList<Entity> listOfQuestionAnswers = new ArrayList<Entity>();

        Elements lines = tableElement.select("tr");

        for (Element line : lines) {

            Elements currentRowValue = line.select("td");
            String question = currentRowValue.get(0).text();
            String answer = currentRowValue.get(1).text();

            Entity entity = new Entity(this.category, sectionTitle, question, answer);

            // todo Double quote is replaced with \"
            listOfQuestionAnswers.add(entity);
        }
        return listOfQuestionAnswers;
    }
}
