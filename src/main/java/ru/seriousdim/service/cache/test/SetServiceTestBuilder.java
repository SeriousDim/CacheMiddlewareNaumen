package ru.seriousdim.service.cache.test;

import org.junit.jupiter.api.Assertions;
import ru.seriousdim.parser.model.InputData;
import ru.seriousdim.service.cache.basic.ICacheService;

import java.util.ArrayList;
import java.util.Arrays;

public class SetServiceTestBuilder
    implements IServiceTestBuilder<Long, Long>
{

    private ICacheService<Long, Long> service;

    private InputData<Long> inputData;

    private Long excepted;

    public SetServiceTestBuilder() {
        super();
    }

    @Override
    public IServiceTestBuilder<Long, Long> testService(ICacheService<Long, Long> service) {
        this.service = service;

        return this;
    }

    @Override
    public IServiceTestBuilder<Long, Long> createInputData(long totalRequests, int cacheLength)
            throws ServiceTestBuildingException {
        this.inputData = new InputData<>(totalRequests, cacheLength, new ArrayList<>());

        if (this.service == null) {
            throw new ServiceTestBuildingException("You must call testService(IService) method first");
        }

        this.service.setSizeLimit(cacheLength);

        return this;
    }

    @Override
    public IServiceTestBuilder<Long, Long> appendIds(Long... ids)
            throws ServiceTestBuildingException {
        if (this.inputData == null) {
            throw new ServiceTestBuildingException("You must call createInputData(long, long) before this method.");
        }

        Arrays
                .stream(ids)
                .forEach((item) -> this.inputData.appendId(item));

        return this;
    }

    @Override
    public IServiceTestBuilder<Long, Long> appendIds(Integer... ids)
            throws ServiceTestBuildingException {
        if (this.inputData == null) {
            throw new ServiceTestBuildingException("You must call createInputData(long, long) before this method.");
        }

        Arrays
                .stream(ids)
                .map(Integer::toUnsignedLong)
                .forEach((item) -> this.inputData.appendId(item));

        return this;
    }

    @Override
    public IServiceTestBuilder<Long, Long> expect(Long expected) {
        this.excepted = expected;

        return this;
    }

    @Override
    public void startAssert() {
        var answer = this.service.analyseAll(this.inputData.getIds());
        Assertions.assertEquals(this.excepted, (long) answer);
    }
}
