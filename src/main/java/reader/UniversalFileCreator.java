package reader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UniversalFileCreator {

    private Path basePath;

    public UniversalFileCreator(String basePath)  {
        this.basePath = Paths.get(basePath);
    }

    public void createFile(String row, String fileName) {

        try {
            Path filePath = this.basePath.resolve(fileName);
            FileWriter fileWriter = new FileWriter(filePath.toFile(), true);
            BufferedWriter out = new BufferedWriter(fileWriter);
            out.write(row);
            out.newLine();
            out.close();
        }catch (IOException e){
            System.out.println(e);
        }
    }
}
