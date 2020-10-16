package readerTest;

import org.junit.Test;
import reader.readers.websiteReader.HTMLReader;
import reader.readers.wikiReader.Entity;
import reader.readers.wikiReader.WebPageReader;
import reader.readers.wikiReader.webpagereader.ActorReader;
import reader.readers.wikiReader.webpagereader.AwardReader;
import reader.readers.wikiReader.webpagereader.FilmReader;

import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class HTMLReaderTest {

    @Test
    public void checkFilmDirectorName() throws IOException {
        FilmReader htmlReader = new FilmReader("https://en.wikipedia.org/wiki/Sanju");
        String directorName = htmlReader.getDirectedBy();

        assertEquals("{\"value\":\"Rajkumar Hirani\",\"href\":\"/wiki/Rajkumar_Hirani\"}",directorName);
    }

    @Test
    public void checkActorBorn() throws IOException {
        ActorReader htmlReader = new ActorReader("https://en.wikipedia.org/wiki/Shah_Rukh_Khan");
        String bornIn = htmlReader.getYearsActive();

        assertEquals("{\"value\":\"1988–present\"}",bornIn);
    }

    @Test
  /*/  public void checkListOfActors() throws IOException {
        ActorReader htmlReader = new ActorReader("https://en.wikipedia.org/wiki/Shah_Rukh_Khan");
        List<Entity> listOfActors = htmlReader.getListOfActors();
        List<Entity> expecteListOfActors ;
        assertArrayEquals(bornInExpected,bornIn);
    }*/

    public void checkFilmFareAwards() throws IOException {
        AwardReader actorReader = new AwardReader("https://en.wikipedia.org/wiki/39th_Filmfare_Awards");
        Entity bestSupportingActor = actorReader.getBestSupportingActor();

        assertEquals("Sunny Deol – Damini",bestSupportingActor.getName());
    }

    @Test
    public void checkTableParsed() throws IOException {

        HTMLReader htmlReader = new HTMLReader("https://www.vincent-luciani.com/php-tutorial/index.html","/(.*?)-tutorial/.*");

        htmlReader.readKnowledgeTables();

    }
}

