package ru.seriousdim.parser.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс-обертка для входных данных заданий
 * @param <IdType> тип идентификатора (id) запроса
 */
public class InputData<IdType> {

    private long totalRequests;
    private long cacheLength;
    private List<IdType> ids;

    public InputData() {
        this.ids = new ArrayList<>();
    }

    public InputData(long totalRequests, long cacheLength, List<IdType> ids) {
        this.totalRequests = totalRequests;
        this.cacheLength = cacheLength;
        this.ids = ids;
    }

    public long getTotalRequests() {
        return totalRequests;
    }

    public void setTotalRequests(long totalRequests) {
        this.totalRequests = totalRequests;
    }

    public long getCacheLength() {
        return cacheLength;
    }

    public void setCacheLength(long cacheLength) {
        this.cacheLength = cacheLength;
    }

    public List<IdType> getIds() {
        return ids;
    }

    public void setIds(List<IdType> ids) {
        this.ids = ids;
    }

    /**
     * Добавить идентификатор (id) запроса
     * @param id идентифиатор (id) запроса
     */
    public void appendId(IdType id) {
        this.getIds().add(id);
    }

}
