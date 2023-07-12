package ru.seriousdim.service.cache.basic;

import java.util.List;

/**
 * Интерфейс для сервисов кэша
 * @param <IdType> тип идентификатора (id) запроса
 * @param <Answer> тип значения, которое мы хотим посчитать или получить исходя из данных
 */
public interface ICacheService<IdType, Answer extends Number> {

    /**
     * Прогнать все id и посчитать ответ
     * @param ids список id
     * @return ответ
     */
    Answer analyseAll(List<IdType> ids);

    /**
     * Удалить из кэша элемент, который с наименьшей вероятностью нам пригодится
     */
    void removeMostUselessElement();

    void reset();

    void setSizeLimit(int size);

    int getSizeLimit();

}
