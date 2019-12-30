package DispatcherTest;

import dispatcher.DispatcherConfigurationManager;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class DispatcherConfigurationManagerTest {

    DispatcherConfigurationManager dispatcherConfigurationManager;

    @Test
    public void checkDirectoriesConstructed() throws IOException {

        Path basePath = Paths.get("C:\\test_java\\collector\\src\\test\\testArtifacts\\00_all_directories_present");
        File logFile = new File("C:\\test_java\\logfile.txt");
        LogFileAnalyzer logFileAnalyzer = new LogFileAnalyzer(logFile);

        dispatcherConfigurationManager = new DispatcherConfigurationManager(basePath);

       Path directoryWithOldFiles = dispatcherConfigurationManager.getDirectoryWithOldFiles();
       Path directoryWithNewFiles = dispatcherConfigurationManager.getDirectoryWithNewFiles();
       Path directoryWithFilesToDelete = dispatcherConfigurationManager.getDirectoryWithFilesToDelete();
       Path directoryWithFilesToUpdate = dispatcherConfigurationManager.getDirectoryWithFilesToUpdate();
       Path directoryWithFilesToAdd = dispatcherConfigurationManager.getDirectoryWithFilesToAdd();

       assertEquals("C:\\test_java\\collector\\src\\test\\testArtifacts\\00_all_directories_present\\work\\directoryWithOldFiles",directoryWithOldFiles.toString());
       assertEquals("C:\\test_java\\collector\\src\\test\\testArtifacts\\00_all_directories_present\\work\\directoryWithNewFiles",directoryWithNewFiles.toString());
       assertEquals("C:\\test_java\\collector\\src\\test\\testArtifacts\\00_all_directories_present\\work\\directoryWithFilesToDelete",directoryWithFilesToDelete.toString());
       assertEquals("C:\\test_java\\collector\\src\\test\\testArtifacts\\00_all_directories_present\\work\\directoryWithFilesToUpdate",directoryWithFilesToUpdate.toString());
       assertEquals("C:\\test_java\\collector\\src\\test\\testArtifacts\\00_all_directories_present\\work\\directoryWithFilesToAdd",directoryWithFilesToAdd.toString());


        ArrayList<LogFileAnalyzer.LogFileLineContent> logFileContent = logFileAnalyzer.getLogFileContent();

        assertEquals(0,logFileContent.size());
     }

    @Test
    public void checkOldDirectoryMissing() throws IOException {

        File logFile = new File("C:\\test_java\\logfile.txt");
        LogFileAnalyzer logFileAnalyzer = new LogFileAnalyzer(logFile);

        Path basePath2 = Paths.get("C:\\test_java\\collector\\src\\test\\testArtifacts\\01_missing_directoryWithOldFiles");
        dispatcherConfigurationManager = new DispatcherConfigurationManager(basePath2);

        ArrayList<LogFileAnalyzer.LogFileLineContent> logFileContent = logFileAnalyzer.getLogFileContent();

        assertEquals("ERROR",logFileContent.get(0).level);
        assertEquals("Missing directory: C:\\test_java\\collector\\src\\test\\testArtifacts\\01_missing_directoryWithOldFiles\\work\\directoryWithOldFiles",logFileContent.get(0).message);

    }
}
