package dispatcher;

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

    private boolean validateDirectory(Path directoryToValidate){
        String missingDirectoryMessage =  String.format("Missing directory: %s",directoryToValidate);
        String isNotADirectoryMessage =   String.format("Following is not a directory: %s",directoryToValidate);

        if (!directoryToValidate.toFile().exists()){
            logger.error(missingDirectoryMessage);
            return false;
        }

        if (!directoryToValidate.toFile().isDirectory()){
            logger.error(isNotADirectoryMessage);
            return false;
        }

        return true;
    }
    private boolean validateDispatcherDirectoryStructure(){

        Boolean isDirectoryStructureValid;

        isDirectoryStructureValid = validateDirectory(this.directoryWithOldFiles)
                                    && validateDirectory(this.directoryWithNewFiles)
                                    && validateDirectory(this.directoryWithFilesToDelete)
                                    && validateDirectory(this.directoryWithFilesToUpdate)
                                    && validateDirectory(this.directoryWithFilesToAdd);

        return isDirectoryStructureValid;
    }
}


