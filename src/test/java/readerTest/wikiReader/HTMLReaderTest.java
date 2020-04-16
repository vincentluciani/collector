package readerTest.wikiReader;

import org.junit.Test;
import reader.websiteReader.HTMLReader;
import reader.wikiReader.WebPageReader;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class HTMLReaderTest {

    @Test
    public void checkStringExtracted() throws IOException {
        WebPageReader htmlReader = new WebPageReader("https://en.wikipedia.org/wiki/Sanju");
        String directorName = htmlReader.getDirectedBy();

        assertEquals("{\"value:\"Rajkumar Hirani\",\"href:\"/wiki/Rajkumar_Hirani\"}",directorName);
    }

    @Test
    public void checkTableParsed() throws IOException {

        HTMLReader htmlReader = new HTMLReader("http://www.vincent-luciani.com/php-tutorial.html");

        htmlReader.readKnowledgeTables();

    }
}
