package dispatcher;

import org.apache.commons.io.FileUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileDispatcher {

    private DispatcherConfigurationManager dispatcherConfigurationManager;

    public FileDispatcher(DispatcherConfigurationManager dispatcherConfigurationManager){
        this.dispatcherConfigurationManager = dispatcherConfigurationManager;
    }

    public void dispatchNewOrUpdatedFile(Path fileToCompare) throws IOException {

        Path fileInNewDirectory = this.dispatcherConfigurationManager.getDirectoryWithNewFiles().resolve(fileToCompare);
        Path fileInOldDirectory = this.dispatcherConfigurationManager.getDirectoryWithOldFiles().resolve(fileToCompare);

        if (!Files.exists(fileInOldDirectory)) {
            FileUtils.copyFile(fileInNewDirectory.toFile(), this.dispatcherConfigurationManager.getDirectoryWithFilesToAdd().resolve(fileToCompare).toFile());
        } else if (Files.exists(fileInNewDirectory) && !FileUtils.contentEquals(fileInOldDirectory.toFile(), fileInNewDirectory.toFile())) {
            FileUtils.copyFile(fileInNewDirectory.toFile(), this.dispatcherConfigurationManager.getDirectoryWithFilesToUpdate().resolve(fileToCompare).toFile());
        }

    }
    public void dispatchDeletedFile(Path fileToCompare) throws IOException {

        Path fileInNewDirectory = this.dispatcherConfigurationManager.getDirectoryWithNewFiles().resolve(fileToCompare);
        Path fileInOldDirectory = this.dispatcherConfigurationManager.getDirectoryWithOldFiles().resolve(fileToCompare);

        if (!Files.exists(fileInNewDirectory)) {
            FileUtils.copyFile(fileInOldDirectory.toFile(), this.dispatcherConfigurationManager.getDirectoryWithFilesToDelete().resolve(fileToCompare).toFile());
        }

    }

}
