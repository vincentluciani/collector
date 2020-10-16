package reader;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;

public interface Reader {
    void readAndOutputToUniversalFile() throws SQLException, IOException, ParserConfigurationException, SAXException;
}
