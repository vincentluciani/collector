package reader.readers.websiteReader;

import manager.LogicalNodeConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import reader.Reader;
import reader.utils.UniversalFileCreator;

import java.io.IOException;
import java.util.ArrayList;

public class SitemapReader implements Reader {

    private static final Logger logger = LogManager.getLogger(SitemapReader.class.getName());
    Document HTMLDocument;
    String url;
    String protocol;
    boolean isProxy;
    private LogicalNodeConfigurationManager logicalNodeConfigurationManager;
    private String urlPattern;
    private String categoryPattern;

    private String lastProcessedIdentification;

    /*  public SitemapReader(String sitemapURL, String urlPattern, String protocol, boolean isProxy) throws IOException {
          this.url = sitemapURL;
          this.protocol = protocol;
          this.isProxy = isProxy;
          this.urlPattern = urlPattern;
      }*/
    public SitemapReader(LogicalNodeConfigurationManager logicalNodeConfigurationManager, String lastProcessedIdentification) {

        this.logicalNodeConfigurationManager = logicalNodeConfigurationManager;
        this.lastProcessedIdentification = lastProcessedIdentification;
        this.url = this.logicalNodeConfigurationManager.getSitemapUrl();
        this.protocol = this.logicalNodeConfigurationManager.getSitemapProtocol();
        this.isProxy = this.logicalNodeConfigurationManager.getSitemapIsProxy() == "true";
        this.urlPattern = this.logicalNodeConfigurationManager.getUrlPattern();
        this.categoryPattern = this.logicalNodeConfigurationManager.getCategoryPattern();

        logger.info("Sitemap URL:" + this.url);
    }


    public ArrayList<String> getURLList() throws IOException {

        ArrayList<String> listOfURLs = new ArrayList<String>();

        URLReaderWithMatch urlReaderWithMatch = new URLReaderWithMatch(this.url,
                this.protocol,
                this.isProxy);

        listOfURLs = urlReaderWithMatch.getListOfURLs(urlPattern);

        return listOfURLs;
    }

    public void readAndOutputToUniversalFile() throws IOException {

        readAndOutputToUniversalFile(this.lastProcessedIdentification);
    }

    public void readAndOutputToUniversalFile(String lastIdentificationNumber) throws IOException {

        ArrayList<Entity> finalListOfQuestionAnswers = new ArrayList<Entity>();
        ArrayList<String> listOfURLs = new ArrayList<String>();

        listOfURLs = getURLList();

        // go through each page of the sitemap to add elements to the array of entities
        for (String url : listOfURLs) {

            HTMLReader htmlReader = new HTMLReader(url, this.categoryPattern);
            ArrayList<Entity> listOfQuestionAnswers = new ArrayList<Entity>();

            listOfQuestionAnswers = htmlReader.readKnowledgeTables();
            finalListOfQuestionAnswers.addAll(listOfQuestionAnswers);

        }

        // go through each entity and add it to the universal file
        Integer i = 0;
        for (Entity currentEntity : finalListOfQuestionAnswers) {
            String documentName = url + "_" + i.toString();
            i++;

            String row = "_${";
            StringBuilder stringBuilder = new StringBuilder(row);

            stringBuilder.append(currentEntity.getCategory())
                    .append("};_${")
                    .append(currentEntity.getSubcategory())
                    .append("};_${")
                    .append(currentEntity.getQuestion())
                    .append("};_${")
                    .append(currentEntity.getAnswer())
                    .append("};");

            row = stringBuilder.toString();
            UniversalFileCreator universalFileCreator = new UniversalFileCreator(this.logicalNodeConfigurationManager.getReaderOutputBasePath(),
                    this.logicalNodeConfigurationManager.getDestinationDataPool(), this.logicalNodeConfigurationManager.getDestinationSubDataPool());
            universalFileCreator.createFile(row, documentName);
            this.lastProcessedIdentification = documentName;

        }

    }
}
