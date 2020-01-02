package dispatcher;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class FilesDispatcher {

    private DispatcherConfigurationManager dispatcherConfigurationManager;
    private FileDispatcher fileDispatcher;

    public FilesDispatcher(DispatcherConfigurationManager dispatcherConfigurationManager) throws IOException {

        this.dispatcherConfigurationManager = dispatcherConfigurationManager;

    }

    public void scanNewFilesDirectory(){

        this.fileDispatcher = new FileDispatcher(this.dispatcherConfigurationManager);

        try (DirectoryStream<Path> workingDirectoryStream = Files.newDirectoryStream(this.dispatcherConfigurationManager.getDirectoryWithNewFiles(), "*" )) {
            for (Path currentFileOrDirectory : workingDirectoryStream) {


                this.fileDispatcher.dispatchNewOrUpdatedFile(currentFileOrDirectory.getFileName());

            }
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public void scanOldFilesDirectory(){

        try (DirectoryStream<Path> workingDirectoryStream = Files.newDirectoryStream(this.dispatcherConfigurationManager.getDirectoryWithOldFiles(),"*")) {
            for (Path currentFileOrDirectory : workingDirectoryStream) {

                this.fileDispatcher.dispatchDeletedFile(currentFileOrDirectory.getFileName());
            }
        } catch (IOException e) {
            System.out.println(e);
        }

    }

}
