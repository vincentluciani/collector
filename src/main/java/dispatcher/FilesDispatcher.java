package dispatcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilesDispatcher {

    private DispatcherConfigurationManager dispatcherConfigurationManager;
    private FileDispatcher fileDispatcher;
    private static final Logger logger = LogManager.getLogger(FilesDispatcher.class.getName());

    public FilesDispatcher(DispatcherConfigurationManager dispatcherConfigurationManager) {

        this.dispatcherConfigurationManager = dispatcherConfigurationManager;

    }

    public void scanNewFilesDirectory(){

        this.fileDispatcher = new FileDispatcher(this.dispatcherConfigurationManager);

        try (DirectoryStream<Path> workingDirectoryStream = Files.newDirectoryStream(this.dispatcherConfigurationManager.getDirectoryWithNewFiles(), "*" )) {
            for (Path currentFileOrDirectory : workingDirectoryStream) {


                this.fileDispatcher.dispatchNewOrUpdatedFile(currentFileOrDirectory.getFileName());

            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }

    public void scanOldFilesDirectory(){

        try (DirectoryStream<Path> workingDirectoryStream = Files.newDirectoryStream(this.dispatcherConfigurationManager.getDirectoryWithOldFiles(),"*")) {
            for (Path currentFileOrDirectory : workingDirectoryStream) {

                this.fileDispatcher.dispatchDeletedFile(currentFileOrDirectory.getFileName());
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }

}
