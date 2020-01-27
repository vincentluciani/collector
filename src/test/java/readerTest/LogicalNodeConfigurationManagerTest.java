package readerTest;

import org.junit.Before;
import org.junit.Test;
import reader.LogicalNodeConfigurationManager;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

import static junit.framework.TestCase.assertEquals;

public class LogicalNodeConfigurationManagerTest {

    public LogicalNodeConfigurationManager logicalNodeConfigurationManager;

    @Before
    public void initialize() throws IOException {
        logicalNodeConfigurationManager = new LogicalNodeConfigurationManager(Paths.get("C:\\test_java\\collector\\src\\test\\testArtifacts"),"us");
    }

    @Test
    public void checkDatabaseConfigurationRead() throws IOException {

        assertEquals("DATABASE_NAME", logicalNodeConfigurationManager.getDatabaseName());
        assertEquals("DATABASE_HOST", logicalNodeConfigurationManager.getDatabaseHost());
        assertEquals("DATABASE_PORT", logicalNodeConfigurationManager.getDatabasePort());
        assertEquals("DATABASE_USER", logicalNodeConfigurationManager.getDatabaseUser());
        assertEquals("DATABASE_PASSWORD#&$&^&##(%*#&(*&%(#*&(%$&--!!!!", logicalNodeConfigurationManager.getDatabasePassword());
        assertEquals("DATABASE_INSTANCE", logicalNodeConfigurationManager.getDatabaseInstance());

    }

    @Test
    public void checkDataSelectionTemplate(){
        String expectedTemplate="select ID,NAME from tableName where parameterid=${parameterId} and LANGUAGE=${language}";
        assertEquals(expectedTemplate, logicalNodeConfigurationManager.getDataSelectionTemplate());

    }

    @Test
    public void checkIdentificationColumnName(){
        assertEquals("OID", logicalNodeConfigurationManager.getIdentificationColumnName());

    }

    @Test
    public void checkIdentificationColumnNumber(){
        assertEquals(Integer.valueOf(1), logicalNodeConfigurationManager.getIdentificationColumnNumber());
    }

    @Test
    public void checkOutputBasePath(){
        assertEquals("C:\\test_java\\collector\\src\\test\\testArtifacts\\output", logicalNodeConfigurationManager.getOutputBasePath().toString());
    }
    @Test
    public void checkBatchForUploadBasePath(){
        assertEquals("C:\\test_java\\collector\\src\\test\\testArtifacts\\batches_to_upload", logicalNodeConfigurationManager.getBatchForUploadBasePath().toString());
    }
    @Test
    public void checkReadBatchSize(){
        assertEquals("1000", logicalNodeConfigurationManager.getReaderBatchSize().toString());
    }

    @Test
    public void checkWriterBatchSize(){
        assertEquals("2", logicalNodeConfigurationManager.getWriterBatchSize().toString());
    }

    @Test
    public void checkDestinationDataPool(){
        assertEquals("products", logicalNodeConfigurationManager.getDestinationDataPool().toString());
    }

    @Test
    public void checkCriteriaSet(){

        Map<String,String> expectedCriteriaSet = new TreeMap<>();
        expectedCriteriaSet.put("parameterId","345");
        expectedCriteriaSet.put("language","French");

        assertEquals(expectedCriteriaSet.toString(), logicalNodeConfigurationManager.getListOfCriteria().toString());
    }

    @Test
    public void checkOutputCreationTemplate(){
        String expectedTemplate="{\"${action}\":{\"_id\":\"${id}\"}}\n{\"field0\":\"${0}\",\"field1\":\"${1}\",\"field2\":\"${2}\"}";
        assertEquals(expectedTemplate, logicalNodeConfigurationManager.getOutputCreationTemplate());
    }
}
