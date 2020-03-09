package reader;

import manager.LogicalNodeConfigurationManager;

import java.sql.SQLException;

public class ReaderFactory {

    public static Reader getReader(String readerType, LogicalNodeConfigurationManager logicalNodeConfigurationManager, String lastProcessedIdentification) throws SQLException {
        if ( readerType == "oracle"){
            return new OracleDatabaseReader(logicalNodeConfigurationManager,lastProcessedIdentification);
        }
        return null;
    }
}
