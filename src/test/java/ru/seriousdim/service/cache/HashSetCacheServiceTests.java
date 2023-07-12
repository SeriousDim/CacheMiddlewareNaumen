package ru.seriousdim.service.cache;

import ru.seriousdim.service.cache.HashSetCacheService;
import ru.seriousdim.service.cache.basic.SetCacheService;
import ru.seriousdim.service.cache.basic.SetCacheServiceTests;

public class HashSetCacheServiceTests
    extends SetCacheServiceTests
{

    @Override
    protected SetCacheService<?> getService() {
        return new HashSetCacheService();
    }

}
