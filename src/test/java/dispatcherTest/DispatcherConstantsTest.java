package dispatcherTest;

import dispatcher.DispatcherConstants;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DispatcherConstantsTest {

    @Test
    public void areConstantsSetOK(){
        assertEquals("work/directoryWithOldFiles",DispatcherConstants.DIRECTORY_WITH_OLD_FILES);
        assertEquals("work/directoryWithNewFiles",DispatcherConstants.DIRECTORY_WITH_NEW_FILES);
        assertEquals("work/directoryWithFilesToDelete",DispatcherConstants.DIRECTORY_WITH_FILES_TO_DELETE);
        assertEquals("work/directoryWithFilesToUpdate", DispatcherConstants.DIRECTORY_WITH_FILES_TO_UPDATE);
        assertEquals("work/directoryWithFilesToAdd", DispatcherConstants.DIRECTORY_WITH_FILES_TO_ADD);
        assertEquals("logs", DispatcherConstants.DIRECTORY_WITH_LOGS);
    }
}
