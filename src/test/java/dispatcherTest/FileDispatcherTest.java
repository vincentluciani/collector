package dispatcherTest;

import dispatcher.DispatcherConfigurationManager;
import dispatcher.FileDispatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

import static junit.framework.TestCase.assertEquals;

@RunWith(Parameterized.class)
public class FileDispatcherTest {
    private String baseDirectory;
    private String fileName;
    private boolean isInUpdateFolder;
    private boolean isInAddFolder;
    private boolean isInDeleteFolder;

    private DispatcherConfigurationManager dispatcherConfigurationManager;

    // constructor
    public FileDispatcherTest(String baseDirectory, String fileName, boolean isInUpdateFolder, boolean isInAddFolder, boolean isInDeleteFolder){
        this.baseDirectory = baseDirectory;
        this.fileName = fileName;
        this.isInUpdateFolder = isInUpdateFolder;
        this.isInAddFolder = isInAddFolder;
        this.isInDeleteFolder = isInDeleteFolder;
    }

    @Parameterized.Parameters
    public static Collection testNumbers(){
        return Arrays.asList(new Object[][]
                {
                        {"C:\\test_java\\collector\\src\\test\\testArtifacts\\dispatcher_test_directory","a1.txt",false,false,false},
                        {"C:\\test_java\\collector\\src\\test\\testArtifacts\\dispatcher_test_directory","a2.txt",false,true,false},
                        {"C:\\test_java\\collector\\src\\test\\testArtifacts\\dispatcher_test_directory","a3.txt",true,false,false},
                        {"C:\\test_java\\collector\\src\\test\\testArtifacts\\dispatcher_test_directory","a5.txt",false,false,true},
                });
    }

    @Test
    public void testWithArray() throws IOException {

        Path basePath = Paths.get(this.baseDirectory);

        dispatcherConfigurationManager = new DispatcherConfigurationManager(basePath);

        FileDispatcher fileDispatcher = new FileDispatcher(dispatcherConfigurationManager);

        Path fileToTest = Paths.get(this.fileName);
        fileDispatcher.dispatchNewOrUpdatedFile(fileToTest);
        fileDispatcher.dispatchDeletedFile(fileToTest);

        Path destinationFileUpdate = Paths.get( this.baseDirectory+"\\work\\directoryWithFilesToUpdate\\"+this.fileName);
        assertEquals(this.isInUpdateFolder,Files.exists(destinationFileUpdate));

        Path destinationFileAdd = Paths.get( this.baseDirectory+"\\work\\directoryWithFilesToAdd\\"+this.fileName);
        assertEquals(this.isInAddFolder,Files.exists(destinationFileAdd));

        Path destinationFileDelete = Paths.get( this.baseDirectory+"\\work\\directoryWithFilesToDelete\\"+this.fileName);
        assertEquals(this.isInDeleteFolder,Files.exists(destinationFileDelete));


    }
}
