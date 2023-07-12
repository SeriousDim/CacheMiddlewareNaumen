package ru.seriousdim.collections.implementations;

import ru.seriousdim.collections.basic.ILimitedCollection;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * {@link TreeSet}, который может хранить в себе только ограниченное число элементов
 * @param <E> - тип элементов, которые будут хранить в коллекции
 */
public class LimitedTreeSet<E>
    extends TreeSet<E>
    implements ILimitedCollection
{

    public static final int DEFAULT_SIZE = 10;

    private int sizeLimit;

    public LimitedTreeSet() {
        this(DEFAULT_SIZE);
    }

    public LimitedTreeSet(Comparator<? super E> comparator) {
        super(comparator);
        this.sizeLimit = DEFAULT_SIZE;
    }

    public LimitedTreeSet(int size) {
        super();
        this.sizeLimit = size;
    }

    public LimitedTreeSet(int size, Comparator<? super E> comparator) {
        super(comparator);
        this.sizeLimit = size;
    }

    /**
     * Добавляет элемент в коллекцию, если в ней есть место и возвращает true,
     * иначе не добавляет и возвращает false.
     * 
     * Использует реализацию {@link TreeSet#add(Object)}
     *
     * @param e - элемент, который пытаемся добавить в колекцию
     * @return был ли добавлен е в коллекцию
     */
    @Override
    public boolean add(E e) {
        if (!this.isFull()) {
            return super.add(e);
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
