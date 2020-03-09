package reader;

import java.sql.SQLException;

public interface Reader {
    public void readAndOutputToUniversalFile() throws SQLException;
}
