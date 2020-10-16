package reader;

import manager.LogicalNodeConfigurationManager;
import reader.readers.XMLFileReader.XMLFileReader;
import reader.readers.databaseReader.OracleDatabaseReader;
import reader.readers.websiteReader.SitemapReader;

import java.sql.SQLException;

public class ReaderFactory {

    public static Reader getReader(String readerType, LogicalNodeConfigurationManager logicalNodeConfigurationManager, String lastProcessedIdentification) throws SQLException {
        if (readerType.equalsIgnoreCase("oracle")) {
            return new OracleDatabaseReader(logicalNodeConfigurationManager, lastProcessedIdentification);
        } else if (readerType.equalsIgnoreCase("sitemap")) {
            return new SitemapReader(logicalNodeConfigurationManager, lastProcessedIdentification);
        } else if (readerType.equalsIgnoreCase("xml")) {
            return new XMLFileReader(logicalNodeConfigurationManager, lastProcessedIdentification);
        } else {
            System.out.println("no such type of reader:" + readerType.toLowerCase());
        }
        return null;
    }
}
