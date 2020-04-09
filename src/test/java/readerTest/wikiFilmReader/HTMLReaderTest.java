package readerTest.wikiFilmReader;

import org.junit.Test;
import reader.wikiFilmReader.HTMLReader;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class HTMLReaderTest {

    @Test
    public void checkStringExtracted() throws IOException {
        HTMLReader htmlReader = new HTMLReader("https://en.wikipedia.org/wiki/Sanju");
        String directorName = htmlReader.getDirectedBy();

        assertEquals("{\"value:\"Rajkumar Hirani\",\"href:\"/wiki/Rajkumar_Hirani\"}",directorName);
    }
}