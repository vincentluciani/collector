package dispatcher;

import java.nio.file.Files;
import java.nio.file.Path;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DispatcherConfigurationManager {

    private Path baseDirectory;
    private static final Logger logger = LogManager.getLogger(DispatcherConfigurationManager.class.getName());

    @Getter private Path directoryWithOldFiles;
    @Getter private Path directoryWithNewFiles;
    @Getter private Path directoryWithFilesToDelete;
    @Getter private Path directoryWithFilesToUpdate;
    @Getter private Path directoryWithFilesToAdd;
    @Getter private Path logDirectory;
    @Getter private Boolean isConfigurationSuccessful;

    public DispatcherConfigurationManager(Path baseDirectory)  {
        this.baseDirectory = baseDirectory;
        constructDispatcherDirectoryStructure();
        this.isConfigurationSuccessful = validateDispatcherDirectoryStructure();
    }

    private void constructDispatcherDirectoryStructure(){

        this.directoryWithOldFiles          = this.baseDirectory.resolve(DispatcherConstants.DIRECTORY_WITH_OLD_FILES);
        this.directoryWithNewFiles          = this.baseDirectory.resolve(DispatcherConstants.DIRECTORY_WITH_NEW_FILES);
        this.directoryWithFilesToDelete     = this.baseDirectory.resolve(DispatcherConstants.DIRECTORY_WITH_FILES_TO_DELETE);
        this.directoryWithFilesToUpdate     = this.baseDirectory.resolve(DispatcherConstants.DIRECTORY_WITH_FILES_TO_UPDATE);
        this.directoryWithFilesToAdd        = this.baseDirectory.resolve(DispatcherConstants.DIRECTORY_WITH_FILES_TO_ADD);
        this.logDirectory                   = this.baseDirectory.resolve(DispatcherConstants.DIRECTORY_WITH_LOGS);
    }

    private boolean validateDispatcherDirectoryStructure(){

        String missingOldFileDirectoryMessage = String.format("Missing directory containing old files: %s",this.directoryWithOldFiles);
        String missingNewFileDirectoryMessage = String.format("Missing directory containing new files: %s",this.directoryWithNewFiles);
        String missingFilesToDeleteDirectoryMessage = String.format("Missing directory containing files to delete: %s",this.directoryWithFilesToDelete);
        String missingFilesToUpdateDirectoryMessage = String.format("Missing directory containing files to update: %s",this.directoryWithFilesToUpdate);
        String missingFilesToAddDirectoryMessage = String.format("Missing directory containing files to add: %s",this.directoryWithFilesToAdd);

        if (!Files.exists(this.directoryWithOldFiles) || !Files.isDirectory(this.directoryWithOldFiles)){
            logger.error(missingOldFileDirectoryMessage);
            return false;
        }

        if (!Files.exists(this.directoryWithNewFiles) || !Files.isDirectory(this.directoryWithNewFiles)){
            logger.error(missingNewFileDirectoryMessage);
            return false;
        }

        if (!Files.exists(this.directoryWithFilesToDelete) || !Files.isDirectory(this.directoryWithFilesToDelete)){
            logger.error(missingFilesToDeleteDirectoryMessage);
            return false;
        }

        if (!Files.exists(this.directoryWithFilesToUpdate) || !Files.isDirectory(this.directoryWithFilesToUpdate)){
            logger.error(missingFilesToUpdateDirectoryMessage);
            return false;
        }

        if (!Files.exists(this.directoryWithFilesToAdd) || !Files.isDirectory(this.directoryWithFilesToAdd)){
            logger.error(missingFilesToAddDirectoryMessage);
            return false;
        }

        return true;
    }
}


