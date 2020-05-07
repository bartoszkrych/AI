package krych.bartosz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DataSaver {

    private String fileName;
    private String folderName;

    public DataSaver(String folderName, String fileName) {
        this.fileName = fileName;
        this.folderName = folderName;
    }

    public void saveToFile(List<String[]> dataLines, String ext) {
        existFolder(folderName);
        File outputFile = new File(folderName + "/" + fileName + "." + ext);
        try (PrintWriter pw = new PrintWriter(outputFile)) {
            dataLines.stream()
                    .map(s -> String.join(";", s))
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    private void existFolder(String target) {
        final Path path = Paths.get(target);
        if (Files.notExists(path)) {
            try {
                Files.createFile(Files.createDirectories(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
