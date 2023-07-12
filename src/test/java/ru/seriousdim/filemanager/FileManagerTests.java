package ru.seriousdim.filemanager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class FileManagerTests {

    private final String FILENAME = "file_manager_tests_file.txt";

    private void createFile(String... lines) throws IOException {
        var path = Paths.get(FILENAME);

        Files.write(path, Arrays.stream(lines).toList());
    }

    @AfterEach
    public void afterEach() throws IOException {
        var path = Paths.get(FILENAME);

        if (Files.exists(path)) {
            Files.delete(path);
        }
    }

    @Test
    public void testRead_existingFile() throws IOException {
        createFile("Hello", "World");

        var reader = FileManager.read(FILENAME);

        Assertions.assertEquals("Hello", reader.readLine());
        Assertions.assertEquals("World", reader.readLine());
        Assertions.assertNull(reader.readLine());

        reader.close();
    }

    @Test
    public void testRead_fileDoesntExist() {
        Assertions.assertThrows(IOException.class, () -> {
            FileManager.read(FILENAME);
        }, String.format("No such file: %s", FILENAME));
    }

    @Test
    public void testWrite_createAndWriteIterable() throws IOException {
        FileManager.write(FILENAME, new ArrayList<String>() {{
            add("Hello");
            add("World");
        }});

        var reader = FileManager.read(FILENAME);

        Assertions.assertEquals("Hello", reader.readLine());
        Assertions.assertEquals("World", reader.readLine());
        Assertions.assertNull(reader.readLine());

        reader.close();
    }

    @Test
    public void testWrite_createAndWriteString() throws IOException {
        FileManager.write(FILENAME, "Hello\nWorld");

        var reader = FileManager.read(FILENAME);

        Assertions.assertEquals("Hello", reader.readLine());
        Assertions.assertEquals("World", reader.readLine());
        Assertions.assertNull(reader.readLine());

        reader.close();
    }

    @Test
    public void testWrite_rewriteIterable() throws IOException {
        FileManager.write(FILENAME, new ArrayList<String>() {{
            add("Hello");
            add("World");
        }});

        FileManager.write(FILENAME, new ArrayList<String>() {{
            add("My");
            add("Info");
        }});

        var reader = FileManager.read(FILENAME);

        Assertions.assertEquals("My", reader.readLine());
        Assertions.assertEquals("Info", reader.readLine());
        Assertions.assertNull(reader.readLine());

        reader.close();
    }

    @Test
    public void testWrite_rewriteString() throws IOException {
        FileManager.write(FILENAME, new ArrayList<String>() {{
            add("Hello\n");
            add("World");
        }});

        FileManager.write(FILENAME, "My\nInfo");

        var reader = FileManager.read(FILENAME);

        Assertions.assertEquals("My", reader.readLine());
        Assertions.assertEquals("Info", reader.readLine());
        Assertions.assertNull(reader.readLine());

        reader.close();
    }

}
