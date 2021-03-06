package dispatcherTest;

import dispatcher.DispatcherConfigurationManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static junit.framework.TestCase.assertEquals;

@RunWith(Parameterized.class)
public class DispatcherConfigurationManagerTest {
    private String baseDirectory;
    private int expectedNumberOfErrors;
    private String expectedErrorMessage;

    private DispatcherConfigurationManager dispatcherConfigurationManager;
    private static File resourcesDirectory = new File("src");
    private static Path testPath = resourcesDirectory.toPath().resolve("test").resolve("testArtifacts");

    // constructor
    public DispatcherConfigurationManagerTest(String baseDirectory, int expectedNumberOfErrors, String expectedErrorMessage){

        this.baseDirectory = baseDirectory;
        this.expectedNumberOfErrors = expectedNumberOfErrors;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    @Parameterized.Parameters
    public static Collection testNumbers(){
        return Arrays.asList(new Object[][]
                {
                        {testPath.resolve("01_missing_directoryWithOldFiles").toAbsolutePath().toString(),1,"Missing directory: C:\\test_java\\collector\\src\\test\\testArtifacts\\01_missing_directoryWithOldFiles\\work\\directoryWithOldFiles"},
                        {testPath.resolve("00_all_directories_present").toAbsolutePath().toString(),0,""},
                        {testPath.resolve("02_missing_directoryWithNewFiles").toAbsolutePath().toString(),1,"Missing directory: C:\\test_java\\collector\\src\\test\\testArtifacts\\02_missing_directoryWithNewFiles\\work\\directoryWithNewFiles"},
                        {testPath.resolve("04_directoryWithNewfilesIsFile").toAbsolutePath().toString(),1,"Following is not a directory: C:\\test_java\\collector\\src\\test\\testArtifacts\\04_directoryWithNewfilesIsFile\\work\\directoryWithNewFiles"},
                        {testPath.resolve("05_directoryWithOldfilesIsFile").toAbsolutePath().toString(),1,"Following is not a directory: C:\\test_java\\collector\\src\\test\\testArtifacts\\05_directoryWithOldfilesIsFile\\work\\directoryWithOldFiles"},
                        {testPath.resolve("06_missing_directory_update").toAbsolutePath().toString(),1,"Missing directory: C:\\test_java\\collector\\src\\test\\testArtifacts\\06_missing_directory_update\\work\\directoryWithFilesToUpdate"},
                        {testPath.resolve("07_missing_directory_add").toAbsolutePath().toString(),1,"Missing directory: C:\\test_java\\collector\\src\\test\\testArtifacts\\07_missing_directory_add\\work\\directoryWithFilesToAdd"},
                        {testPath.resolve("08_missing_directory_delete").toAbsolutePath().toString(),1,"Missing directory: C:\\test_java\\collector\\src\\test\\testArtifacts\\08_missing_directory_delete\\work\\directoryWithFilesToDelete"},
                        {testPath.resolve("06b_directory_update_is_file").toAbsolutePath().toString(),1,"Following is not a directory: C:\\test_java\\collector\\src\\test\\testArtifacts\\06b_directory_update_is_file\\work\\directoryWithFilesToUpdate"},
                        {testPath.resolve("07b_directory_add_is_file").toAbsolutePath().toString(),1,"Following is not a directory: C:\\test_java\\collector\\src\\test\\testArtifacts\\07b_directory_add_is_file\\work\\directoryWithFilesToAdd"},
                        {testPath.resolve("08b_directory_delete_is_file").toAbsolutePath().toString(),1,"Following is not a directory: C:\\test_java\\collector\\src\\test\\testArtifacts\\08b_directory_delete_is_file\\work\\directoryWithFilesToDelete"},
                        {"C:\\test_javaz",1,"Missing directory: C:\\test_javaz\\work\\directoryWithOldFiles"}
                });
    }

    @Test
    public void testWithArray() throws IOException {
        System.out.println("Testing with base directory:"+baseDirectory);

        Path basePath = Paths.get(this.baseDirectory);
        File logFile = new File("C:\\test_java\\logfile.txt");
        LogFileAnalyzer logFileAnalyzer = new LogFileAnalyzer(logFile);

        dispatcherConfigurationManager = new DispatcherConfigurationManager(basePath);

        ArrayList<LogFileAnalyzer.LogFileLineContent> logFileContent = logFileAnalyzer.getLogFileContent();

        int numberOfErrors = logFileContent.size();
        Assert.assertEquals(this.expectedNumberOfErrors,numberOfErrors);

        if (numberOfErrors != 0) {
            Assert.assertEquals(this.expectedErrorMessage, logFileContent.get(0).message);
        }

        Path directoryWithOldFiles = dispatcherConfigurationManager.getDirectoryWithOldFiles();
        Path directoryWithNewFiles = dispatcherConfigurationManager.getDirectoryWithNewFiles();
        Path directoryWithFilesToDelete = dispatcherConfigurationManager.getDirectoryWithFilesToDelete();
        Path directoryWithFilesToUpdate = dispatcherConfigurationManager.getDirectoryWithFilesToUpdate();
        Path directoryWithFilesToAdd = dispatcherConfigurationManager.getDirectoryWithFilesToAdd();

        Assert.assertEquals(this.baseDirectory+"\\work\\directoryWithOldFiles",directoryWithOldFiles.toString());
        Assert.assertEquals(this.baseDirectory+"\\work\\directoryWithNewFiles",directoryWithNewFiles.toString());
        Assert.assertEquals(this.baseDirectory+"\\work\\directoryWithFilesToDelete",directoryWithFilesToDelete.toString());
        Assert.assertEquals(this.baseDirectory+"\\work\\directoryWithFilesToUpdate",directoryWithFilesToUpdate.toString());
        Assert.assertEquals(this.baseDirectory+"\\work\\directoryWithFilesToAdd",directoryWithFilesToAdd.toString());

    }
}
