package reader;

import manager.LogicalNodeConfigurationManager;
import reader.XMLFileReader.XMLFileReader;
import reader.databaseReader.OracleDatabaseReader;
import reader.websiteReader.SitemapReader;

import java.sql.SQLException;

public class ReaderFactory {

    public static Reader getReader(String readerType, LogicalNodeConfigurationManager logicalNodeConfigurationManager, String lastProcessedIdentification) throws SQLException {
        if ( readerType.toLowerCase().equals("oracle")){
            return new OracleDatabaseReader(logicalNodeConfigurationManager,lastProcessedIdentification);
        } else if ( readerType.toLowerCase().equals("sitemap")){
            return new SitemapReader(logicalNodeConfigurationManager,lastProcessedIdentification);
        } else if ( readerType.toLowerCase().equals("xml")) {
            return new XMLFileReader(logicalNodeConfigurationManager, lastProcessedIdentification);
        } else {
            System.out.println("no such type of reader:"+readerType.toLowerCase());
        }
        return null;
    }
}
