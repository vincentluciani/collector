package reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class UniversalFileCreator {

    private Path basePath;

    private static final Logger logger = LogManager.getLogger(UniversalFileCreator.class.getName());

    public UniversalFileCreator(Path basePath)  {
        this.basePath = basePath;
    }

    public void createFile(String row, String fileName) {

        Path filePath = this.basePath.resolve(fileName);

        try (BufferedWriter out = new BufferedWriter(new FileWriter(filePath.toFile(), true))){
            out.write(row);
            out.newLine();
        }catch (IOException e){
            logger.error(e.getMessage());
        }
    }
}
