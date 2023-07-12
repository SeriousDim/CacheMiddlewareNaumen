package ru.seriousdim.service.cache.test;

import ru.seriousdim.service.cache.basic.ICacheService;

public interface IServiceTestBuilder<IdType, Answer extends Number> {

    IServiceTestBuilder<IdType, Answer> testService(ICacheService<IdType, Answer> service);

    IServiceTestBuilder<IdType, Answer> createInputData(long totalRequests, int cacheLength) throws ServiceTestBuildingException;

    IServiceTestBuilder<IdType, Answer> appendIds(IdType... ids) throws ServiceTestBuildingException;

    IServiceTestBuilder<IdType, Answer> appendIds(Integer... ids) throws ServiceTestBuildingException;

    IServiceTestBuilder<IdType, Answer> expect(Answer expected);

    void startAssert();

}
