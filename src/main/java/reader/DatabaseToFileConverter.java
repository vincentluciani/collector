package reader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Map;

public class DatabaseToFileConverter {

    private ResultSetMetaData metadata;
    private int columnCount;
    String nodeIdentificator;

    private Connection connection;
    private String databaseQuery;
    private Integer identificationColumnNumber;
    private String identificationColumnName;

    private String lastProcessedIdentification;

    private LogicalNodeConfigurationManager logicalNodeConfigurationManager;

    public DatabaseToFileConverter(LogicalNodeConfigurationManager logicalNodeConfigurationManager, String lastProcessedIdentification) throws SQLException, ClassNotFoundException {
        this.logicalNodeConfigurationManager = logicalNodeConfigurationManager;
        this.lastProcessedIdentification = lastProcessedIdentification;
        buildDatabaseQuery();
        connectToDatabase();
        convertRecordsToFiles(lastProcessedIdentification);
    }

    public void connectToDatabase() throws ClassNotFoundException, SQLException {

       // Class.forName("oracle.jdbc.driver.OracleDriver");

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
        metadata = rs.getMetaData();
        this.columnCount = metadata.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            System.out.println(metadata.getColumnName(i) + ";");
        }
     }

    public void convertRecordsToFiles(String lastIdentificationNumber) {

        try {
            String query = this.databaseQuery.concat(" and ").concat(this.identificationColumnName)
                    .concat(" > ").concat(lastIdentificationNumber).concat(" order by ")
                    .concat(this.identificationColumnName);
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            getMetaData(rs);

            int counter = 0;

            while (rs.next()) {

                try {
                    String documentName = rs.getString(this.identificationColumnNumber);
                    String row;

                    StringBuilder sb = new StringBuilder("");

                    for (int i = 1; i <= this.columnCount; i++) {
                        sb.append(rs.getString(i));
                        sb.append(";");
                     }

                    row=sb.toString();


                    counter++;
                  UniversalFileCreator universalFileCreator = new UniversalFileCreator(this.logicalNodeConfigurationManager.getOutputBasePath());
                  universalFileCreator.createFile(row,documentName);
                    this.lastProcessedIdentification = documentName;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
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