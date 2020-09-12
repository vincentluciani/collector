package reader.databaseReader;

import manager.LogicalNodeConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reader.Reader;
import reader.UniversalFileCreator;

import java.io.IOException;
import java.sql.*;
import java.util.Map;

public class OracleDatabaseReader implements Reader {

    private int columnCount;

    private Connection connection;
    private String databaseQuery;
    private Integer identificationColumnNumber;
    private String identificationColumnName;

    private String lastProcessedIdentification;

    private LogicalNodeConfigurationManager logicalNodeConfigurationManager;
    private static final Logger logger = LogManager.getLogger(OracleDatabaseReader.class.getName());

    public OracleDatabaseReader(LogicalNodeConfigurationManager logicalNodeConfigurationManager, String lastProcessedIdentification) throws SQLException {
        this.logicalNodeConfigurationManager = logicalNodeConfigurationManager;
        this.lastProcessedIdentification = lastProcessedIdentification;
        buildDatabaseQuery();
        connectToDatabase();
    }

    public void readAndOutputToUniversalFile() throws SQLException {

        readAndOutputToUniversalFile(this.lastProcessedIdentification);
    }

    public void connectToDatabase() throws SQLException {

        String url =  String.format("jdbc:oracle:thin:@%s:%s:%s",
                logicalNodeConfigurationManager.getDatabaseHost(),
                logicalNodeConfigurationManager.getDatabasePort(),
                logicalNodeConfigurationManager.getDatabaseInstance());

        this.connection = DriverManager.getConnection(
                url, logicalNodeConfigurationManager.getDatabaseUser(), logicalNodeConfigurationManager.getDatabasePassword());

        // TODO: should be derived from column name and not from configuration
        this.identificationColumnNumber = logicalNodeConfigurationManager.getIdentificationColumnNumber();

        this.identificationColumnName = logicalNodeConfigurationManager.getIdentificationColumnName();

    }

    public void getMetaData(ResultSet rs) throws SQLException {
        ResultSetMetaData metadata;
        metadata = rs.getMetaData();
        this.columnCount = metadata.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            String columnInformation = String.format("%s;",metadata.getColumnName(i));
            logger.info(columnInformation);
        }
     }

    public void readAndOutputToUniversalFile(String lastIdentificationNumber) throws SQLException {


            String query = this.databaseQuery.concat(" and ").concat(this.identificationColumnName)
                    .concat(" > ").concat(lastIdentificationNumber).concat(" order by ")
                    .concat(this.identificationColumnName);

        try (Statement stmt = this.connection.createStatement() ){

            ResultSet rs = stmt.executeQuery(query);
            getMetaData(rs);

            while (rs.next()) {


                    String documentName = rs.getString(this.identificationColumnNumber);
                    String row = "";

                    StringBuilder sb = new StringBuilder("");

                    for (int i = 1; i <= (this.columnCount); i++) {
                        sb.append("_${");
                        sb.append(rs.getString(i));
                        sb.append("};");
                     }

                    row=sb.toString();

                    UniversalFileCreator universalFileCreator = new UniversalFileCreator(this.logicalNodeConfigurationManager.getReaderOutputBasePath(),
                            this.logicalNodeConfigurationManager.getDestinationDataPool(),this.logicalNodeConfigurationManager.getDestinationSubDataPool());
                    universalFileCreator.createFile(row,documentName);
                    this.lastProcessedIdentification = documentName;
            }

        } catch (SQLException | IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void buildDatabaseQuery(){

        String template = this.logicalNodeConfigurationManager.getDataSelectionTemplate();
        Map<String,String> map = this.logicalNodeConfigurationManager.getListOfCriteria();

        for(Map.Entry<String,String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            String stringToReplace = String.format("${%s}",key);
            template = template.replace(stringToReplace,value);

        }

        this.databaseQuery = template;
    }


}