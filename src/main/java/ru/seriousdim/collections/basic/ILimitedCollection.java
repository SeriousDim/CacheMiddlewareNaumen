package ru.seriousdim.collections.basic;

/**
 * Интерфейс коллекции, которая может хранить только ограниченное число элементов
 */
public interface ILimitedCollection {

    boolean isFull();

    int getSizeLimit();

    void setSizeLimit(int size);

}
