package ru.seriousdim.filemanager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {

    public BufferedReader read(String fileName)
            throws IOException
    {
        var file = new File(fileName);

        if (!file.exists()) {
            throw new IOException(String.format("No such file: %s", fileName));
        }

        var reader = new FileReader(file);

        return new BufferedReader(reader);
    }

    public void write(String fileName, Iterable<String> data)
            throws IOException
    {
        var file = new File(fileName);

        if (!file.exists()) {
            throw new IOException(String.format("No such file: %s", fileName));
        }

        var writer = new FileWriter(file);
        var bufferedWriter = new BufferedWriter(writer);

        for (var line: data) {
            bufferedWriter.write(line);
        }
    }

    public void createAndWrite(String fileName, Iterable<String> data)
            throws IOException
    {
        var file = Paths.get(fileName);

        Files.write(file, data, StandardCharsets.UTF_8);
    }

}
