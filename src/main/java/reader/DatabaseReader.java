package reader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class DatabaseReader {

    private ResultSetMetaData metadata;
    private int columnCount;
    String nodeIdentificator;

    private String databaseName;
    private String databaseHost;
    private String databasePort;
    private String databaseUser;
    private String databasePassword;
    private String databaseInstance;
    private String databaseQuery;
    private Connection connection;
    private int identificationColumnNumber;
    private String basePath;

    public DatabaseReader(String nodeIdentificator,int identificationColumnNumber, String basePath) {
        this.nodeIdentificator = nodeIdentificator;
        this.identificationColumnNumber = identificationColumnNumber;
        this.basePath = basePath;
    }

    public void connectToDatabase() throws ClassNotFoundException, SQLException {

       // Class.forName("oracle.jdbc.driver.OracleDriver");

        String url = "jdbc:oracle:thin:@"+this.databaseHost+":"+this.databasePort+":"+this.databaseInstance;

        this.connection = DriverManager.getConnection(
                url, this.databaseUser, this.databasePassword);

    }

    public void getMetaData(ResultSet rs) throws SQLException {
        metadata = rs.getMetaData();
        this.columnCount = metadata.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            System.out.println(metadata.getColumnName(i) + ";");
        }
    }

    public void convertRecordsToFiles() {

        try {
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(this.databaseQuery);
            int counter = 0;
            while (rs.next()) {
                String documentName = rs.getString(identificationColumnNumber);
                try {
                    String row = "";
                    for (int i = 1; i <= this.columnCount; i++) {
                        row += rs.getString(i) + ";";
                    }
                    System.out.println();
                    counter++;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}