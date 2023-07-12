package ru.seriousdim.service.cache.basic;

import ru.seriousdim.collections.basic.ILimitedCollection;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Сервис кэша, использующий коллекции, основанные на {@link Set}
 * @param <Collection> коллекция {@link Set}, используемая в качестве кэша.
 *                    Должна хранить ограниченное кол-во элементов (реализовывать {@link ILimitedCollection})
 */
public abstract class SetCacheService<Collection extends Set<Long> & ILimitedCollection>
    implements ICacheService<Long, Long>
{

    protected Collection cache;

    protected HashMap<Long, Integer> lastPositions;

    public SetCacheService() {

    }

    public Collection getCache() {
        return cache;
    }

    public void setCache(Collection cache) {
        this.cache = cache;
    }

    public HashMap<Long, Integer> getLastPositions() {
        return lastPositions;
    }

    public void setLastPositions(HashMap<Long, Integer> lastPositions) {
        this.lastPositions = lastPositions;
    }

    /**
     * Ищет и сохраняет последние позиции id во входных данных
     * @param ids id запросов
     */
    private void findLastPositions(List<Long> ids) {
        setLastPositions(new HashMap<>());
        var lastPositions = getLastPositions();

        var size = ids.size();
        for (var index = 0; index < size; index++) {
            lastPositions.put(ids.get(index), index);
        }
    }

    /**
     * Прогоняет все данные id запросов и, используя информацию в {@link SetCacheService#lastPositions},
     * определяет, сколько раз мы бы обратились к внешнему серверу
     * @param ids id запросов
     * @return сколько раз мы бы обратились к внешнему серверу
     */
    private Long findAnswer(List<Long> ids) {
        var result = 0L;
        var cache = getCache();
        var size = ids.size();

        for (var index = 0; index < size; index++) {
            var currentId = ids.get(index);

            if (cache.contains(currentId)) {
                if (index == lastPositions.get(currentId)) {
                    cache.remove(currentId);
                }
            } else {
                result += 1;

                if (index != lastPositions.get(currentId)) {
                    if (cache.isFull()) {
                        this.removeMostUselessElement();
                    }

                    cache.add(currentId);
                }
            }
        }

        return result;
    }

    /**
     * Реализация алгоритма поиска ответа
     * @param ids id запросов
     * @return ответ: сколько раз мы бы обратились к внешнему серверу
     */
    @Override
    public Long analyseAll(List<Long> ids) {
        findLastPositions(ids);

        return findAnswer(ids);
    }

    @Override
    public void reset() {
        this.getCache().clear();
    }

    @Override
    public void setSizeLimit(int size) {
        this.cache.setSizeLimit(size);
    }

    @Override
    public int getSizeLimit() {
        return this.cache.getSizeLimit();
    }
}
