package ru.seriousdim.service.cache.basic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import ru.seriousdim.service.cache.test.ServiceTestBuildingException;
import ru.seriousdim.service.cache.test.SetServiceTestBuilder;

import java.util.Arrays;


@Timeout(value = 3)
public abstract class SetCacheServiceTests {

    protected abstract SetCacheService<?> getService();

    // Test from task

    @Test
    public void testAnalyseAll_fromTask() throws ServiceTestBuildingException {
        new SetServiceTestBuilder()
                .testService(getService())
                .createInputData(15, 5)
                .appendIds(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5, 8, 7, 9, 3)
                .expect(9L)
                .startAssert();
    }

    // Lower bound tests

    @Test
    public void testAnalyseAll_lowerBound1_differentIds() throws ServiceTestBuildingException {
        new SetServiceTestBuilder()
                .testService(getService())
                .createInputData(5, 1)
                .appendIds(1, 2, 3, 4, 5)
                .expect(5L)
                .startAssert();
    }

    @Test
    public void testAnalyseAll_lowerBound2_sameIds() throws ServiceTestBuildingException {
        new SetServiceTestBuilder()
                .testService(getService())
                .createInputData(5, 1)
                .appendIds(2, 2, 2, 2, 2)
                .expect(1L)
                .startAssert();
    }

    @Test
    public void testAnalyseAll_lowerBound3() throws ServiceTestBuildingException {
        new SetServiceTestBuilder()
                .testService(getService())
                .createInputData(4, 1)
                .appendIds(2, 2, 3, 3)
                .expect(2L)
                .startAssert();
    }

    @Test
    public void testAnalyseAll_lowerBound4_sameFirstAndLast() throws ServiceTestBuildingException {
        new SetServiceTestBuilder()
                .testService(getService())
                .createInputData(5, 1)
                .appendIds(2, 2, 3, 3, 2)
                .expect(3L)
                .startAssert();
    }

    @Test
    public void testAnalyseAll_lowerBound5_oneId() throws ServiceTestBuildingException {
        new SetServiceTestBuilder()
                .testService(getService())
                .createInputData(1, 1)
                .appendIds(1)
                .expect(1L)
                .startAssert();
    }

    @Test
    public void testAnalyseAll_lowerBound6_sameFirstAndLastPairs() throws ServiceTestBuildingException {
        new SetServiceTestBuilder()
                .testService(getService())
                .createInputData(6, 1)
                .appendIds(2, 2, 3, 3, 2, 2)
                .expect(3L)
                .startAssert();
    }

    @Test
    public void testAnalyseAll_lowerBound7_differentIds() throws ServiceTestBuildingException {
        new SetServiceTestBuilder()
                .testService(getService())
                .createInputData(3, 1)
                .appendIds(2, 5, 7)
                .expect(3L)
                .startAssert();
    }

    // Various tests

    @Test
    public void testAnalyseAll_various1() throws ServiceTestBuildingException {
        new SetServiceTestBuilder()
                .testService(getService())
                .createInputData(2, 2)
                .appendIds(2, 3)
                .expect(2L)
                .startAssert();
    }

    @Test
    public void testAnalyseAll_various2() throws ServiceTestBuildingException {
        new SetServiceTestBuilder()
                .testService(getService())
                .createInputData(3, 2)
                .appendIds(2, 3, 2)
                .expect(2L)
                .startAssert();
    }

    @Test
    public void testAnalyseAll_various3() throws ServiceTestBuildingException {
        new SetServiceTestBuilder()
                .testService(getService())
                .createInputData(9, 4)
                .appendIds(1, 2, 3, 4, 1, 2, 3, 4, 5)
                .expect(5L)
                .startAssert();
    }

    @Test
    public void testAnalyseAll_various4() throws ServiceTestBuildingException {
        new SetServiceTestBuilder()
                .testService(getService())
                .createInputData(6, 2)
                .appendIds(1, 2, 3, 3, 2, 1)
                .expect(4L)
                .startAssert();
    }

    // Upper bound tests

    @Test
    public void testAnalyseAll_upperBound1_maxCache_oneId() throws ServiceTestBuildingException {
        new SetServiceTestBuilder()
                .testService(getService())
                .createInputData(1, 100000)
                .appendIds(2)
                .expect(1L)
                .startAssert();
    }

    @Test
    public void testAnalyseAll_upperBound2_maxCache_differentIds() throws ServiceTestBuildingException {
        var ids = new Integer[100000];

        for (var index = 0; index < ids.length; index++) {
            ids[index] = index + 1;
        }

        new SetServiceTestBuilder()
                .testService(getService())
                .createInputData(100000, 100000)
                .appendIds(ids)
                .expect(100000L)
                .startAssert();
    }

    @Test
    public void testAnalyseAll_upperBound3_maxCache_sameIds() throws ServiceTestBuildingException {
        var ids = new Integer[100000];

        Arrays.fill(ids, 1);

        new SetServiceTestBuilder()
                .testService(getService())
                .createInputData(100000, 100000)
                .appendIds(ids)
                .expect(1L)
                .startAssert();
    }

    @Test
    public void testAnalyseAll_upperBound4_maxCache_evensAreBigNumbers() throws ServiceTestBuildingException {
        var ids = new Long[100000];

        var odd = (long) (Math.pow(2, 63)) - 1L;
        var even = 1L;

        for (var index = 0; index < ids.length; index++) {
            if (index % 2 == 0) {
                ids[index] = even;
                even++;
            } else {
                ids[index] = odd;
                odd--;
            }
        }

        new SetServiceTestBuilder()
                .testService(getService())
                .createInputData(100000, 100000)
                .appendIds(ids)
                .expect(100000L)
                .startAssert();
    }

    @Test
    public void testAnalyseAll_upperBound5_maxCache_allBigNumbers() throws ServiceTestBuildingException {
        var ids = new Long[100000];

        var value = (long) (Math.pow(2, 63)) - 1L;

        for (var index = 0; index < ids.length; index++) {
            ids[index] = value;
            value--;
        }

        new SetServiceTestBuilder()
                .testService(getService())
                .createInputData(100000, 100000)
                .appendIds(ids)
                .expect(100000L)
                .startAssert();
    }

    @Test
    public void testAnalyseAll_upperBound6_maxCache_dividedIntoHalves() throws ServiceTestBuildingException {
        var ids = new Long[100000];

        for (var index = 0; index < ids.length / 2; index++) {
            ids[index] = 1L;
        }

        for (var index = ids.length / 2; index < ids.length; index++) {
            ids[index] = 2L;
        }

        new SetServiceTestBuilder()
                .testService(getService())
                .createInputData(100000, 100000)
                .appendIds(ids)
                .expect(2L)
                .startAssert();
    }

    @Test
    public void testAnalyseAll_upperBound7_oftenCleaningCache1() throws ServiceTestBuildingException {
        var ids = new Long[100000];

        for (var index = 0; index < ids.length / 2; index++) {
            ids[index] = (long) index + 1;
            ids[ids.length - index - 1] = (long) index + 1;
        }

        new SetServiceTestBuilder()
                .testService(getService())
                .createInputData(100000, 10000)
                .appendIds(ids)
                .expect(90000L)
                .startAssert();
    }

    @Test
    public void testAnalyseAll_upperBound8_oftenCleaningCache2() throws ServiceTestBuildingException {
        var ids = new Long[100000];

        for (var index = 0; index < ids.length / 2; index++) {
            ids[index] = (long) index + 1;
            ids[ids.length - index - 1] = (long) index + 1;
        }

        new SetServiceTestBuilder()
                .testService(getService())
                .createInputData(100000, 1000)
                .appendIds(ids)
                .expect(99000L)
                .startAssert();
    }

    @Test
    public void testAnalyseAll_upperBound9_bigNumbers1() throws ServiceTestBuildingException {
        var ids = new Long[10];

        var value = (long) (Math.pow(2, 63)) - 1L;

        for (var index = 0; index < ids.length; index++) {
            ids[index] = value;
            value -= 9;
        }

        new SetServiceTestBuilder()
                .testService(getService())
                .createInputData(10, 5)
                .appendIds(ids)
                .expect(10L)
                .startAssert();
    }

    @Test
    public void testAnalyseAll_upperBound10_bigNumbers2() throws ServiceTestBuildingException {
        var ids = new Long[5];

        for (var index = 0; index < ids.length; index++) {
            ids[index] = (long) (Math.pow(2, 62 - 4 * index));
        }

        new SetServiceTestBuilder()
                .testService(getService())
                .createInputData(5, 2)
                .appendIds(ids)
                .expect(5L)
                .startAssert();
    }

    @Test
    public void testAnalyseAll_upperBound11_bigNumbers3() throws ServiceTestBuildingException {
        var value = (long) (Math.pow(2, 63)) - 1;

        new SetServiceTestBuilder()
                .testService(getService())
                .createInputData(2, 1)
                .appendIds(value, value)
                .expect(1L)
                .startAssert();
    }

}
