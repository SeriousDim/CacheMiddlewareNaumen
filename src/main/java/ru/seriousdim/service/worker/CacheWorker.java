package ru.seriousdim.service.worker;

import ru.seriousdim.filemanager.FileManager;
import ru.seriousdim.parser.InputParser;
import ru.seriousdim.parser.exceptions.ParsingException;
import ru.seriousdim.service.cache.TreeSetCacheService;
import ru.seriousdim.service.cache.basic.ICacheService;

import java.io.IOException;

public class CacheWorker {

    public static final String OUTPUT_FILENAME = "output.txt";

    private ICacheService<Long, Long> cacheService;
    private InputParser parser;

    public CacheWorker () {
        this.cacheService = new TreeSetCacheService();
        this.parser = new InputParser();
    }

    public CacheWorker(ICacheService<Long, Long> cacheService, InputParser parser) {
        this.cacheService = cacheService;
        this.parser = parser;
    }

    public ICacheService<Long, Long> getCacheService() {
        return cacheService;
    }

    public Long analyse(String fileName)
            throws IOException, ParsingException {
        var bufferedReader = FileManager.read(fileName);

        var inputData = this.parser.parse(bufferedReader);

        bufferedReader.close();

        this.cacheService.setSizeLimit((int) inputData.getCacheLength());
        var answer = this.cacheService.analyseAll(inputData.getIds());

        FileManager.write(OUTPUT_FILENAME, answer.toString());

        return answer;
    }

}
