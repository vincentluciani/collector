package reader;

import manager.LogicalNodeConfigurationManager;
import reader.websiteReader.SitemapReader;

import java.sql.SQLException;

public class ReaderFactory {

    public static Reader getReader(String readerType, LogicalNodeConfigurationManager logicalNodeConfigurationManager, String lastProcessedIdentification) throws SQLException {
        if ( readerType.toLowerCase().equals("oracle")){
            return new OracleDatabaseReader(logicalNodeConfigurationManager,lastProcessedIdentification);
        }
        if ( readerType.toLowerCase().equals("sitemap")){
            return new SitemapReader(logicalNodeConfigurationManager,lastProcessedIdentification);
        } else {
            System.out.println("no such type of reader:"+readerType.toLowerCase());
        }
        return null;
    }
}
