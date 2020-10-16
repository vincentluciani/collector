package reader.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class UniversalFileCreator {

    private static final Logger logger = LogManager.getLogger(UniversalFileCreator.class.getName());
    private Path basePath;
    private String logicalNode;
    private String logicalSubNode;
    private Path folderForLocale;
    private Path folderForSubLocale;

    public UniversalFileCreator(Path basePath, String logicalNode, String logicalSubNode) throws IOException {
        this.basePath = basePath;
        this.logicalNode = logicalNode;
        this.logicalSubNode = logicalSubNode;
        createDirectoriesIfNotExists();
    }

    public void createFile(String row, String fileName) {

        fileName = fileName.replaceAll("([^a-zA-Z\\d])", "_");

        Path filePath = this.folderForSubLocale.resolve(fileName);

        row = row.replaceAll("\\\\", "\\\\\\\\");
        row = row.replaceAll("\"", "\\\\\"");


        try (BufferedWriter out = new BufferedWriter(new FileWriter(filePath.toFile(), true))) {
            out.write(row);
            out.newLine();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void createDirectoriesIfNotExists() throws IOException {

        this.folderForLocale = basePath.resolve(this.logicalNode);

        if (!Files.isDirectory(this.folderForLocale)) {
            Files.createDirectories(this.folderForLocale);
        }

        this.folderForSubLocale = folderForLocale.resolve(this.logicalSubNode);

        if (!Files.isDirectory(this.folderForSubLocale)) {
            Files.createDirectories(this.folderForSubLocale);
        }

    }

}
