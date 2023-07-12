package ru.seriousdim.filemanager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {

    /**
     * Прочитать файл
     * @param fileName имя файла
     * @return BufferedReader с содержимым файла
     * @throws IOException
     */
    public static BufferedReader read(String fileName)
            throws IOException
    {
        var file = new File(fileName);

        if (!file.exists()) {
            throw new IOException(String.format("No such file: %s", fileName));
        }

        var reader = new FileReader(file);

        return new BufferedReader(reader);
    }

    /**
     * Записать в файл несколько строк. Если файла не существует, он будет создан
     * @param fileName имя файла
     * @param data коллекция со строками для записи
     * @throws IOException
     */
    public static void write(String fileName, Iterable<String> data)
            throws IOException
    {
        var file = Paths.get(fileName);

        Files.write(file, data, StandardCharsets.UTF_8);
    }

    /**
     * Записать в файл строку. Если файла не существует, он будет создан
     * @param fileName имя файла
     * @param data строка для записи
     * @throws IOException
     */
    public static void write(String fileName, String data)
            throws IOException
    {
        var file = Paths.get(fileName);

        Files.writeString(file, data, StandardCharsets.UTF_8);
    }

}
