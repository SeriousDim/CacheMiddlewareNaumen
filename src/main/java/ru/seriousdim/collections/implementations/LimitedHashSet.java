package ru.seriousdim.collections.implementations;

import ru.seriousdim.collections.basic.ILimitedCollection;

import java.util.HashSet;

/**
 * {@link HashSet}, который может хранить в себе только ограниченное число элементов.
 * Обратите внимание также на {@link LimitedTreeSet}
 *
 * @param <V> - тип элементов, которые будут хранить в коллекции
 *
 * @see LimitedTreeSet
 */
public class LimitedHashSet<V>
    extends HashSet<V>
    implements ILimitedCollection
{

    /**
     * Это значение не позволяет коллекции менять свою capacity, так как в этом
     * нет необходимости, а также это может занимать дополнительные ресурсы
     */
    private static final float LOAD_FACTOR_TO_PROHIBIT_INCREASING = 2f;
    public static final int DEFAULT_SIZE = 10;

    private int sizeLimit;

    public LimitedHashSet() {
        this(DEFAULT_SIZE);
    }

    public LimitedHashSet(int size) {
        super(size, LOAD_FACTOR_TO_PROHIBIT_INCREASING);
        this.sizeLimit = size;
    }

    /**
     * Добавляет элемент в коллекцию, если в ней есть место и возвращает true,
     * иначе не добавляет и возвращает false.
     *
     * Использует реализацию {@link HashSet#add(Object)}
     *
     * @param v - элемент, который пытаемся добавить в колекцию
     * @return был ли добавлен е в коллекцию
     */
    @Override
    public boolean add(V v) {
        if (this.size() <= getSizeLimit()) {
            return super.add(v);
        }

        return false;
    }

    @Override
    public boolean isFull() {
        return this.size() == getSizeLimit();
    }

    @Override
    public int getSizeLimit() {
        return sizeLimit;
    }

    @Override
    public void setSizeLimit(int limitSize) {
        this.sizeLimit = limitSize;
    }
}
