package DispatcherTest;

import dispatcher.DispatcherConfigurationManager;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class DispatcherConfigurationManagerTest {

    DispatcherConfigurationManager dispatcherConfigurationManager;

    @Before
    public void initializeObject(){
        Path basePath = Paths.get("C:\\test_java\\collector\\src\\test\\testArtifacts");
        dispatcherConfigurationManager = new DispatcherConfigurationManager(basePath);
    }
    @Test
    public void checkDirectoriesConstructed(){

       Path directoryWithOldFiles = dispatcherConfigurationManager.getDirectoryWithOldFiles();
       Path directoryWithNewFiles = dispatcherConfigurationManager.getDirectoryWithNewFiles();
       Path directoryWithFilesToDelete = dispatcherConfigurationManager.getDirectoryWithFilesToDelete();
       Path directoryWithFilesToUpdate = dispatcherConfigurationManager.getDirectoryWithFilesToUpdate();
       Path directoryWithFilesToAdd = dispatcherConfigurationManager.getDirectoryWithFilesToAdd();

       assertEquals("C:\\test_java\\collector\\src\\test\\testArtifacts\\work\\directoryWithOldFiles",directoryWithOldFiles.toString());
       assertEquals("C:\\test_java\\collector\\src\\test\\testArtifacts\\work\\directoryWithNewFiles",directoryWithNewFiles.toString());
       assertEquals("C:\\test_java\\collector\\src\\test\\testArtifacts\\work\\directoryWithFilesToDelete",directoryWithFilesToDelete.toString());
       assertEquals("C:\\test_java\\collector\\src\\test\\testArtifacts\\work\\directoryWithFilesToUpdate",directoryWithFilesToUpdate.toString());
       assertEquals("C:\\test_java\\collector\\src\\test\\testArtifacts\\work\\directoryWithFilesToAdd",directoryWithFilesToAdd.toString());

    }

    

}
