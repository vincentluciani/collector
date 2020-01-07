package readerTest;

import org.junit.Before;
import org.junit.Test;
import reader.LogicalNodeConfigurationManager;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

import static junit.framework.TestCase.assertEquals;

public class ReaderConfigurationManagerTest {

    public LogicalNodeConfigurationManager readerConfigurationManager;

    @Before
    public void initialize() throws IOException {
        readerConfigurationManager = new LogicalNodeConfigurationManager(Paths.get("C:\\test_java\\collector\\src\\test\\testArtifacts"),"us");
    }

    @Test
    public void checkDatabaseConfigurationRead() throws IOException {

        assertEquals("DATABASE_NAME",readerConfigurationManager.getDatabaseName());
        assertEquals("DATABASE_HOST",readerConfigurationManager.getDatabaseHost());
        assertEquals("DATABASE_PORT",readerConfigurationManager.getDatabasePort());
        assertEquals("DATABASE_USER",readerConfigurationManager.getDatabaseUser());
        assertEquals("DATABASE_PASSWORD#&$&^&##(%*#&(*&%(#*&(%$&--!!!!",readerConfigurationManager.getDatabasePassword());
        assertEquals("DATABASE_INSTANCE",readerConfigurationManager.getDatabaseInstance());

    }

    @Test
    public void checkDataSelectionTemplate(){
        String expectedTemplate="select ID,NAME from tableName where parameterid=${parameterId} and LANGUAGE=${language}";
        assertEquals(expectedTemplate,readerConfigurationManager.getDataSelectionTemplate());

    }

    @Test
    public void checkIdentificationColumnName(){
        assertEquals("OID",readerConfigurationManager.getIdentificationColumnName());

    }

    @Test
    public void checkIdentificationColumnNumber(){
        assertEquals(Integer.valueOf(1),readerConfigurationManager.getIdentificationColumnNumber());
    }

    @Test
    public void checkOutputBasePath(){
        assertEquals("C:\\test_java\\collector\\src\\test\\testArtifacts\\output",readerConfigurationManager.getOutputBasePath().toString());
    }
    @Test
    public void checkReadBatchSize(){
        assertEquals("1000",readerConfigurationManager.getReaderBatchSize().toString());
    }
    @Test
    public void checkCriteriaSet(){

        Map<String,String> expectedCriteriaSet = new TreeMap<>();
        expectedCriteriaSet.put("parameterId","345");
        expectedCriteriaSet.put("language","French");

        assertEquals(expectedCriteriaSet.toString(),readerConfigurationManager.getListOfCriteria().toString());
    }
}
