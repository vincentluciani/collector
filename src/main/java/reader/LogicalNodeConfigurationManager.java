package reader;

import lombok.Getter;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedBuilderParametersImpl;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
// must add this import and get the corresponding maven dependency
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class LogicalNodeConfigurationManager {

    /* A readerConfigurationManager manages the configurations for one logical node
    logicalNode uses:
     - one physicalDataSource
     - one criteriaSet (can be one or more in the future)
     - one dataSelectionTemplate
     - one outputCreationTemplate

    Naming convention: ${configuration file type}_${id}.properties
    Example:  dataSelectionTemplate_0001.properties

    all configuration files are in a folder called configurations that sits on the basePath
    ${configurationsBasePath}/configurations/logicalNode/logicalNode.properties
        common.physicalDataSource=1
        common.dataSelectionTemplate=1
        common.outputCreationTemplate=1
    ${configurationsBasePath}/configuration/physicalDataSource/physicalDataSource_001.properties
    ${configurationsBasePath}/configuration/criteriaSet/criteriaSet_001.properties
    criteriaparametername1=criteriaparameternamevalue1
    criteriaparametername2=criteriaparameternamevalue2
    ${configurationsBasePath}/configuration/dataSelectionTemplate/dataSelectionTemplate_001.properties
    will contain criteria parameter ${criteriaparametername1} ${criteriaparametername2}
    ${configurationsBasePath}/configuration/outputCreationTemplate/outputCreationTemplate_001.properties

contains
default.propertyname
nodeid.propertyname


    * */
    @Getter private Path logicalNodeConfigurationPath;
    @Getter private Path physicalDataSourceConfigurationPath;
    @Getter private Path criteriaSetConfigurationPath;
    @Getter private Path dataSelectionTemplatePath;
    @Getter private Path outputCreationTemplatePath;
    @Getter private Path outputBasePath;

    @Getter private String databaseName;
    @Getter private String databaseHost;
    @Getter private String databasePort;
    @Getter private String databaseUser;
    @Getter private String databasePassword;
    @Getter private String databaseInstance;

    @Getter private Map<String,String> listOfCriteria;
    @Getter private String dataSelectionTemplate;
    @Getter private Integer identificationColumnNumber;
    @Getter private Integer readerBatchSize;
    @Getter private String identificationColumnName;

    private String logicalNodeID;
    private Path configurationsBasePath;


    private List<Object> criteriaParameters;
    private List<Object> criteriaValues;

    private static final String PROPERTIES_EXTENSION="properties";
    private static final String LOGICAL_NODE="logicalNode";
    private static final String PHYSICAL_DATASOURCE="physicalDataSource";
    private static final String DATA_SELECTION_TEMPLATE="dataSelectionTemplate";
    private static final String OUTPUT_SELECTION_TEMPLATE="outputCreationTemplate";
    private static final String FILE_NAME_FORMAT = "%s_%03d.%s";
    private static final String CONFIGURATION="configuration";
    private static final String CRITERIA_PARAMETERS="criteriaParameters";
    private static final String CRITERIA_VALUES="criteriaValues";
    private static final String IDENTIFICATION_NUMBER="identificationColumnNumber";
    private static final String OUTPUT_BASE_PATH="outputBasePath";
    private static final String READER_BATCH_SIZE="readerBatchSize";
    private static final String IDENTIFICATION_COLUMN_NAME="identificationColumnName";

    private static final Logger logger = LogManager.getLogger(LogicalNodeConfigurationManager.class.getName());

    public LogicalNodeConfigurationManager(Path configurationsBasePath, String logicalNodeID) throws IOException {

        this.configurationsBasePath = configurationsBasePath;
        this.logicalNodeID = logicalNodeID;

        readLogicalNodeConfigurationPaths();
        readPhysicalSourceProperties();
        //readCriteriaSet();
        constructCriteriaSet();
        readDataSelectionTemplate();

    }

    public Configuration readLogicalNodeConfiguration() {

        String configurationFile = "logicalNodes.properties";

        this.logicalNodeConfigurationPath = configurationsBasePath.resolve(CONFIGURATION).resolve(LOGICAL_NODE)
                .resolve(configurationFile);

        return readConfigurationFile(this.logicalNodeConfigurationPath);
    }

    public Configuration readConfigurationFile(Path configurationPath){

        Parameters params = new Parameters();
        Configuration configuration;

        File parameterFile = configurationPath.toFile();

        try
        {
            FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                            .configure(params.properties().setListDelimiterHandler(new DefaultListDelimiterHandler(','))
                                    .setFile(parameterFile).setEncoding("utf8")
                            );

            configuration = builder.getConfiguration();
            return configuration;
        }
        catch(ConfigurationException configurationException)
        {
            logger.error(configurationException);
        }
        return null;
    }

    public String constructConfigurationFileName(String configurationType, String id){

        return String.format(FILE_NAME_FORMAT, configurationType, Integer.valueOf(id), PROPERTIES_EXTENSION);

    }

    public Path getPathConfiguration(String configurationType, Configuration configuration){

        Path pathConfiguration;
        String commonParameter = String.format("common.%s",configurationType);
        String nodeParameter = String.format("%s.%s",this.logicalNodeID,configurationType);
        Object nodeParameterValue = configuration.getProperty(nodeParameter);
        String parameterValue;

        if (nodeParameterValue!=null){
            parameterValue = configuration.getProperty(nodeParameter).toString();
        } else {
            parameterValue = configuration.getProperty(commonParameter).toString();
        }

        pathConfiguration = configurationsBasePath.resolve(CONFIGURATION).resolve(configurationType)
                .resolve( constructConfigurationFileName(configurationType,parameterValue));

        return pathConfiguration;
    }

    public List<Object> getCriteriaConfiguration(String configurationType, Configuration configuration){

        List<Object> criteriaConfiguration;
        String commonParameter = String.format("common.%s",configurationType);
        String nodeParameter = String.format("%s.%s",this.logicalNodeID,configurationType);

        Object nodeParameterValue = configuration.getProperty(nodeParameter);

        if (nodeParameterValue!=null){
            criteriaConfiguration = configuration.getList(nodeParameter);
        } else {
            criteriaConfiguration = configuration.getList(commonParameter);
        }
        return criteriaConfiguration;
    }

    public String getSimpleParameter(String configurationType, Configuration configuration){

        String parameterValue;
        String commonParameter = String.format("common.%s",configurationType);
        String nodeParameter = String.format("%s.%s",this.logicalNodeID,configurationType);

        Object nodeParameterValue = configuration.getProperty(nodeParameter);
        Object commonParameterValue =  configuration.getProperty(commonParameter);

        if (nodeParameterValue!=null){
            parameterValue = nodeParameterValue.toString();
        } else {
            parameterValue = commonParameterValue.toString();
        }
        return parameterValue;
    }

    public void readLogicalNodeConfigurationPaths(){

        Configuration configuration = readLogicalNodeConfiguration();

        this.physicalDataSourceConfigurationPath = getPathConfiguration(PHYSICAL_DATASOURCE, configuration);

        this.dataSelectionTemplatePath = getPathConfiguration(DATA_SELECTION_TEMPLATE, configuration);

        this.outputCreationTemplatePath = getPathConfiguration(OUTPUT_SELECTION_TEMPLATE, configuration);

        this.criteriaParameters = getCriteriaConfiguration(CRITERIA_PARAMETERS,configuration);
        this.criteriaValues = getCriteriaConfiguration(CRITERIA_VALUES,configuration);
        this.outputBasePath = Paths.get(getSimpleParameter(OUTPUT_BASE_PATH,configuration));

        this.identificationColumnNumber = Integer.valueOf(getSimpleParameter(IDENTIFICATION_NUMBER,configuration));
        this.readerBatchSize = Integer.valueOf(getSimpleParameter(READER_BATCH_SIZE,configuration));
        this.identificationColumnName = getSimpleParameter(IDENTIFICATION_COLUMN_NAME,configuration);

    }

    public void readPhysicalSourceProperties(){

        Configuration configuration = readConfigurationFile(this.physicalDataSourceConfigurationPath);

        this.databaseName = configuration.getString("databaseName");
        this.databaseHost = configuration.getString("databaseHost");
        this.databasePort = configuration.getString("databasePort");
        this.databaseUser = configuration.getString("databaseUser");
        this.databasePassword = configuration.getString("databasePassword");
        this.databaseInstance = configuration.getString("databaseInstance");

    }


    public void readCriteriaSet(){

        Configuration configuration = readConfigurationFile(this.criteriaSetConfigurationPath);

        this.listOfCriteria= new TreeMap<>();

        Iterator<String> keys = configuration.getKeys();

        while(keys.hasNext()){
            String key = keys.next();
            String value = configuration.getString(key);
            this.listOfCriteria.put(key,value);
        }

    }

    public void constructCriteriaSet(){

        this.listOfCriteria= new TreeMap<>();

        Iterator<Object> values = this.criteriaValues.iterator();

        for(Object key : this.criteriaParameters){
            if (values.hasNext()) {
                Object value = values.next();
                this.listOfCriteria.put(key.toString(), value.toString());
            }
        }

    }

    public void readDataSelectionTemplate() throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        List<java.lang.String> lines  = FileUtils.readLines(this.dataSelectionTemplatePath.toFile(), StandardCharsets.UTF_8);
        for (java.lang.String line : lines){
            stringBuilder.append(line).append(" ");
        }
        this.dataSelectionTemplate = stringBuilder.toString().trim();
    }

}



