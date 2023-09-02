package org.example.Modules;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Interperter {
    private File newFile;
    private List<String> newCode = new ArrayList<>();
    private boolean addToCodeIndent = false;
    private int indentLevel = 0;

    public void start(List<String> lines, File file) {
        createAndSafeFile(file);

        for (String line : lines) {
            checkKeyWords(line);
        }

        writeToFile();
        run();
    }

    private void createAndSafeFile(File file) {
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


    private void checkKeyWords(String line) {
        String[] words = line.split(" ");

        StringBuilder indentBuilder = new StringBuilder();
        Stream.generate(()-> " ").limit(indentLevel * 4L).forEach(indentBuilder::append);
        newCode.add(indentBuilder.toString());


        StringBuilder lineBuilder = new StringBuilder();
        if (addToCodeIndent) {
            lineBuilder.append(indentBuilder);
        }

        for (String word : words) {
            switch (word) {
                case "so" -> {
                    lineBuilder.append("else ");
                }
                case "soif" -> {
                    lineBuilder.append("elif ");
                }
                case "->" -> {
                    indentLevel++;
                    lineBuilder.append(": ");
                    addToCodeIndent = true;
                }
                case "<-" -> {
                    indentLevel--;
                    addToCodeIndent = false;
                }
                case "empty" -> {
                    lineBuilder.append("None ");
                }
                case "pack" -> {
                    lineBuilder.append("class ");
                }
                case "connect" -> {
                    lineBuilder.append("import ");
                }
                case "block" -> {
                    lineBuilder.append("def ");
                }
                case "false" ->{
                    lineBuilder.append("False ");
                }
                case "true" -> {
                    lineBuilder.append("True");

                }
                case "check" ->{
                    lineBuilder.append("assert ");
                }
                case "like" ->{
                    lineBuilder.append("as ");
                }
                case "use" ->{
                    lineBuilder.append("with ");
                }
                case "stop" ->{
                    lineBuilder.append("break ");
                }
                case "go" ->{
                    lineBuilder.append("continue ");
                }
                case "forgot"->{
                    lineBuilder.append("del ");
                }
                case "every"->{
                    lineBuilder.append("for ");
                }
                case "give"->{
                    lineBuilder.append("return ");
                }
                case "during"->{
                    lineBuilder.append("while ");
                }
                case "generate" ->{
                    lineBuilder.append("yield ");
                }
                case "attempt" -> {
                    lineBuilder.append("try ");
                }
                case "fail" -> {
                    lineBuilder.append("except ");
                }
                case "lastly" ->{
                    lineBuilder.append("finally ");
                }
                case "anon" -> {
                    lineBuilder.append("lambda ");
                }
                case "where" -> {
                    lineBuilder.append("from ");
                }
                case "also" ->
                {
                    lineBuilder.append("and ");
                }
                case "else" ->{
                    lineBuilder.append("or ");
                }
                case "absolute" -> {
                    lineBuilder.append("global ");
                }
                case "outer" -> {
                    lineBuilder.append("nonlocal ");
                }
                case "<!>" -> {
                    lineBuilder.append("not ");
                }
                case "error" ->{
                    lineBuilder.append("raise ");
                }
                case "equals" ->{
                    lineBuilder.append("is ");
                }
                case "has" ->{
                    lineBuilder.append("in ");
                }
                default -> {
                    lineBuilder.append(word).append(" ");
                }
            }
        }

        newCode.add(lineBuilder.toString().trim());
        newCode.add("\n");

    }
    public void run()
    {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python", newFile.getAbsolutePath());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("Код завершения: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
    private void writeToFile() {

        try (FileWriter fileWriter = new FileWriter(newFile);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);) {
            for(String code : newCode)
            {
                bufferedWriter.write(code );
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
