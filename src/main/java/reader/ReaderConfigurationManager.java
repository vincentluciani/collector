package reader;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.nio.file.Path;
import java.sql.ResultSet;

public class ReaderConfigurationManager {

    private String databaseName;
    private String databaseHost;
    private String databasePort;
    private String databaseUser;
    private String databasePassword;
    private String databaseInstance;
    private Configuration configuration;

    public ReaderConfigurationManager(Path databaseConnectionConfigurationFilePath){
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setFileName("C:\\test_java\\databaseaccess.properties"));

        try
        {
            this.configuration = builder.getConfiguration();
        }
        catch(ConfigurationException cex)
        {
            // loading of the configuration file failed
        }

        ResultSet config;
        String backColor = this.configuration.getString("colors.background");

    }



}
