package readerTest;

import org.junit.Test;
import reader.HTMLReader;
import reader.HTMLReader;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class HTMLReaderTest {

    @Test
    public void checkStringExtracted() throws IOException {
        HTMLReader htmlReader = new HTMLReader("https://en.wikipedia.org/wiki/Tanhaji");
        String directorName = htmlReader.getDirectorName();

        assertEquals("Om Raut",directorName);
    }
}