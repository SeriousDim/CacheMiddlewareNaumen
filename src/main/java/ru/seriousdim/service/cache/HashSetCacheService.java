package ru.seriousdim.service.cache;

import ru.seriousdim.collections.implementations.LimitedHashSet;
import ru.seriousdim.service.cache.basic.SetCacheService;

/**
 * Сервис кэша, использующий {@link java.util.HashSet}
 *
 * Этот сервис может плохую производительность на больших данных.
 * Рекомендуется использовать {@link TreeSetCacheService}
 *
 * @see TreeSetCacheService
 */
public class HashSetCacheService
    extends SetCacheService<LimitedHashSet<Long>>
{

    public HashSetCacheService() {
        super();
        this.cache = new LimitedHashSet<>();
    }

    public HashSetCacheService(int cacheLength) {
        super();
        this.cache = new LimitedHashSet<>(cacheLength);
    }

    @Override
    public void removeMostUselessElement() {
        var maximum = cache
                .stream()
                .max((o1, o2) -> lastPositions.get(o1) - lastPositions.get(o2))
                .get();
        getCache().remove(maximum);
    }
}
