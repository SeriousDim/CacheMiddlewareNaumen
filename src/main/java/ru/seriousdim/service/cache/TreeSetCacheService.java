package ru.seriousdim.service.cache;

import ru.seriousdim.collections.implementations.LimitedTreeSet;
import ru.seriousdim.service.cache.basic.SetCacheService;

import java.util.Comparator;

/**
 * Сервис кэша, использующий {@link java.util.TreeSet}
 */
public class TreeSetCacheService
    extends SetCacheService<LimitedTreeSet<Long>>
{

    /**
     * Компаратор, позволяющий дереву правильно сравнивать id запросов друг с другом.
     * Сравниваются последние позиции id запросов во входных данных
     */
    private final Comparator<Long> COMPARATOR =
            (o1, o2) -> lastPositions.get(o1) - lastPositions.get(o2);

    public TreeSetCacheService() {
        super();
        this.cache = new LimitedTreeSet<>(COMPARATOR);
    }

    public TreeSetCacheService(int cacheLength) {
        super();
        this.cache = new LimitedTreeSet<>(cacheLength, COMPARATOR);
    }

    /**
     * Политика выбора кандидата на удаление.
     * Выбирается элемент (id) с самой большой позицией во входных данных.
     * У дерева - просто самый максимальный (последний) элемент
     */
    @Override
    public void removeMostUselessElement() {
        var maximum = getCache().last();
        getCache().remove(maximum);
    }

}
