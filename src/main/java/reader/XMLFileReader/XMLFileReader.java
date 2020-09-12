package reader.XMLFileReader;

import manager.LogicalNodeConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import reader.Reader;
import reader.UniversalFileCreator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;


public class XMLFileReader implements Reader {

    private static final Logger logger = LogManager.getLogger(XMLFileReaderWithHierarchy.class.getName());
    ArrayList<String[]> listOfHierarchies = new ArrayList<String[]>();
    private String lastProcessedIdentification;
    private String[] firstXmlHierarchy = {"company", "team", "staff", "firstname"};
    private String[] secondXmlHierarchy = {"company", "companyName"};
    private LogicalNodeConfigurationManager logicalNodeConfigurationManager;

    public XMLFileReader(LogicalNodeConfigurationManager logicalNodeConfigurationManager, String lastProcessedIdentification) {
        this.logicalNodeConfigurationManager = logicalNodeConfigurationManager;
        this.lastProcessedIdentification = lastProcessedIdentification;
    }

    public void readAndOutputToUniversalFile() throws ParserConfigurationException, SAXException {

        readAndOutputToUniversalFile(this.lastProcessedIdentification, this.logicalNodeConfigurationManager, this.firstXmlHierarchy);
    }

    public void readAndOutputToUniversalFile(String lastIdentificationNumber, LogicalNodeConfigurationManager logicalNodeConfigurationManager, String[] xmlHierarchy) throws ParserConfigurationException, SAXException {

        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {

                String tagType = "";
                String row = "";

                StringBuilder sb = new StringBuilder();


                public void startElement(String uri, String localName, String qName,
                                         Attributes attributes) throws SAXException {

                    System.out.println("Start Element :" + qName);

                }

                public void endElement(String uri, String localName,
                                       String qName) throws SAXException {

                    System.out.println("End Element :" + qName);

                }

                public void endDocument() throws SAXException {

                    row = sb.toString();

                    try {
                        UniversalFileCreator universalFileCreator = new UniversalFileCreator(logicalNodeConfigurationManager.getReaderOutputBasePath(),
                                logicalNodeConfigurationManager.getDestinationDataPool(), logicalNodeConfigurationManager.getDestinationSubDataPool());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("row:" + row);
                    //universalFileCreator.createFile(row,documentName);
                    //this.lastProcessedIdentification = documentName;
                    //   }
                }


                public void characters(char[] ch, int start, int length) throws SAXException {

                    String value = new String(ch, start, length);


                    if ((null != value) && (value.trim().length() > 0)) {
                        System.out.println("Value : " + value);
                    }

                }

            };

            saxParser.parse("C:\\test_java\\input_files\\test.xml", handler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


