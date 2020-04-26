package reader;

import java.io.IOException;
import java.sql.SQLException;

public interface Reader {
    public void readAndOutputToUniversalFile() throws SQLException, IOException;
}
