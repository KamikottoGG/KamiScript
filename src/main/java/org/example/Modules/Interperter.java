package org.example.Modules;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Interperter {
    private File newFile;
    private FileWriter fileWriter;
    private List<String> newCode = new ArrayList<>();
    private boolean addToCodeIndent = false;

    public void start(List<String> lines, File file)
    {
        createAndSafeFile(file);
        createFileWriter();
        for (String line : lines)
        {
            checkKeyWords(line);
        }
        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeToFile();
    }
    private void createAndSafeFile(File file)
    {
        File newFile = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 3) + ".py");
        if (!newFile.exists()) {
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.newFile = newFile;
    }

    private void createFileWriter()
    {
        try {
            fileWriter = new FileWriter(newFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void checkKeyWords(String line)
    {
        String[] words = line.split(" ");
        if (addToCodeIndent) newCode.add("    ");
        for (String word : words)
        {
            switch (word){
                case "so" -> {
                    newCode.add("else");
                }
                case "soif" ->{
                    newCode.add("elif");
                }
                case "->" ->{
                    newCode.add(":");
                    addToCodeIndent = true;

                }
                case "<-" -> {
                    addToCodeIndent = false;
                }
                default -> {
                    newCode.add(" ");
                    newCode.add(word);
                }
            }
        }

        newCode.add("\n");
    }
    private void writeToFile()
    {
        for(String code : newCode)
        {
            System.out.print(code);
        }
    }


}
