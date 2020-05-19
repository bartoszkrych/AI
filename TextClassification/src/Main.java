import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Set<String> classes = new HashSet<>();
        List<String> contents = new ArrayList<>();
        List<String> toSave = new ArrayList<>();
        String arg1 = "@RELATION wikis\n\n@ATTRIBUTE wiki  STRING\n@ATTRIBUTE class    ";
        String arg4 = "@DATA \n";


        File[] files = new File("data").listFiles();
        //If this pathname does not denote a directory, then listFiles() returns null.

        for (File file : files) {
            if (file.isFile()) {
                String name = file.getName().split("_")[0];
                classes.add(name);

                try {
                    BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();

                    while (line != null) {
                        sb.append(line);
                        line = br.readLine();
                    }
                    String everything = sb.toString();
                    everything.replace("nbsp;", " ")
                            .replace(System.getProperty("line.separator"), "").strip();

                    everything = String.join("", everything.split("'"));

                    String content = new StringBuilder().append("'").append(everything).append("',").append(name).append(System.getProperty("line.separator")).toString();
                    contents.add(content);

                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        classes.forEach(System.out::println);

        StringBuilder builderToSave = new StringBuilder();
        builderToSave.append(arg1).append("{");
        List<String> classesList = new ArrayList<>(classes);
        Collections.sort(classesList);
        for (int i = 0; i < classes.size(); i++) {
            builderToSave.append(classesList.get(i));
            if (i != classes.size() - 1) builderToSave.append(",");
        }
        builderToSave.append("}\n\n");
        builderToSave.append(arg4);

        try{
            FileWriter writer = new FileWriter("data.arff");
            writer.write(builderToSave.toString());
            for (String str : contents) {
                writer.write(str);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
