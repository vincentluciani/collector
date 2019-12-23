package DispatcherTest;

import dispatcher.DispatcherConstants;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DispatcherConstantsTest {

    @Test
    public void areConstantsSetOK(){
        assertEquals(DispatcherConstants.DIRECTORY_WITH_OLD_FILES,"work/directoryWithOldFiles");
        assertEquals(DispatcherConstants.DIRECTORY_WITH_NEW_FILES,"work/directoryWithNewFiles");
        assertEquals(DispatcherConstants.DIRECTORY_WITH_FILES_TO_DELETE,"work/directoryWithFilesToDelete");
        assertEquals(DispatcherConstants.DIRECTORY_WITH_FILES_TO_UPDATE,"work/directoryWithFilesToUpdate");
        assertEquals(DispatcherConstants.DIRECTORY_WITH_FILES_TO_ADD,"work/directoryWithFilesToAdd");
        assertEquals(DispatcherConstants.DIRECTORY_WITH_LOGS,"logs");
    }
}
