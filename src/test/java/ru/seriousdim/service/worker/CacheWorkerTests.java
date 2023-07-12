package ru.seriousdim.service.worker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.seriousdim.filemanager.FileManager;
import ru.seriousdim.parser.exceptions.ParsingException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class CacheWorkerTests {

    private final String OUTPUT = "output.txt";
    private final String INPUT = "cache_worker_tests_input.txt";

    private void createFile(String... lines) throws IOException {
        var path = Paths.get(INPUT);

        Files.write(path, Arrays.stream(lines).toList());
    }

    @AfterEach
    public void afterEach() throws IOException {
        var outputPath = Paths.get(OUTPUT);

        if (Files.exists(outputPath)) {
            Files.delete(outputPath);
        }

        var inputPath = Paths.get(INPUT);

        if (Files.exists(inputPath)) {
            Files.delete(inputPath);
        }
    }

    @Test
    public void testAnalyse_checkLimitSize () throws IOException, ParsingException {
        createFile("5 1", "1");

        var cw = new CacheWorker();

        cw.analyse(INPUT);
        Assertions.assertEquals(5, cw.getCacheService().getSizeLimit());
    }

    @Test
    public void testAnalyse_fromTask () throws IOException, ParsingException {
        createFile("5 15", "3", "1", "4", "1", "5",
                "9", "2", "6", "5", "3", "5", "8", "7", "9", "3");

        var cw = new CacheWorker();

        cw.analyse(INPUT);

        var reader = FileManager.read(OUTPUT);

        Assertions.assertEquals("9", reader.readLine());
        Assertions.assertNull(reader.readLine());

        reader.close();
    }

    @Test
    public void testAnalyse_1 () throws IOException, ParsingException {
        var ids = new String[100000 + 1];

        ids[0] = "100000 100000";

        var value = (long) (Math.pow(2, 63)) - 1L;

        for (var index = 0; index < ids.length - 1; index++) {
            ids[index + 1] = value + "";
            value--;
        }

        createFile(ids);

        var cw = new CacheWorker();

        cw.analyse(INPUT);

        var reader = FileManager.read(OUTPUT);

        Assertions.assertEquals("100000", reader.readLine());
        Assertions.assertNull(reader.readLine());

        reader.close();
    }

    @Test
    public void testAnalyse_2 () throws IOException, ParsingException {
        var ids = new String[100000 + 1];

        ids[0] = "10000 100000";

        for (var index = 1; index <= (ids.length - 1) / 2; index++) {
            ids[index] = index + "";
            ids[ids.length - index] = index + "";
        }

        createFile(ids);

        var cw = new CacheWorker();

        cw.analyse(INPUT);

        var reader = FileManager.read(OUTPUT);

        Assertions.assertEquals("90000", reader.readLine());
        Assertions.assertNull(reader.readLine());

        reader.close();
    }

}
