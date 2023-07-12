package ru.seriousdim.service.cache;

import ru.seriousdim.service.cache.TreeSetCacheService;
import ru.seriousdim.service.cache.basic.SetCacheService;
import ru.seriousdim.service.cache.basic.SetCacheServiceTests;

public class TreeSetCacheServiceTests
    extends SetCacheServiceTests
{

    @Override
    protected SetCacheService<?> getService() {
        return new TreeSetCacheService();
    }

}
