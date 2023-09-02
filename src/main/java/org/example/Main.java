package org.example;

import org.example.Modules.Interperter;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.print("Enter a path to file: ");
        String pathRaw = new Scanner(System.in).nextLine();
        Path path = Path.of(pathRaw);
        File file = new File(path.toUri());
        Reader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        List<String> lines = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        reader.close();
        new Interperter().start(lines,file);
    }
}