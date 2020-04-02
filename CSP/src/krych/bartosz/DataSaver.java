package krych.bartosz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class DataSaver {

    private String fileName;
    private String cspType;
    private String folderName;

    public DataSaver(String folderName, String fileName) {
        this.fileName = fileName;
        this.folderName = folderName;
    }

    public void setCspType(String cspType) {
        this.cspType = cspType;
    }

    public void saveToFile(List<String[]> dataLines) {
        File txtOutputFile = new File(folderName + "/" + fileName + "_" + cspType + ".txt");
        try (PrintWriter pw = new PrintWriter(txtOutputFile)) {
            dataLines.stream()
                    .map(s -> String.join(";", s))
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}
