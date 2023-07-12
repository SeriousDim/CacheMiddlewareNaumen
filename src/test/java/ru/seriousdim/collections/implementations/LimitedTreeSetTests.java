package ru.seriousdim.collections.implementations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

public class LimitedTreeSetTests {

    @Test
    public void testDefaultConstructor() {
        var ts = new LimitedTreeSet<>();

        Assertions.assertEquals(LimitedTreeSet.DEFAULT_SIZE, ts.getSizeLimit());
    }

    @Test
    public void testConstructorWithComparator() {
        var comp = Comparator.reverseOrder();
        var ts = new LimitedTreeSet<>(comp);

        Assertions.assertEquals(comp, ts.comparator());
        Assertions.assertEquals(LimitedTreeSet.DEFAULT_SIZE, ts.getSizeLimit());
    }

    @Test
    public void testConstructorWithSize() {
        var size = 2_000_000_000;
        var ts = new LimitedTreeSet<>(size);

        Assertions.assertEquals(size, ts.getSizeLimit());
    }

    @Test
    public void testConstructorWithSizeAndComparator() {
        var comp = Comparator.reverseOrder();
        var size = 2_000_000_000;
        var ts = new LimitedTreeSet<>(size, comp);

        Assertions.assertEquals(size, ts.getSizeLimit());
        Assertions.assertEquals(comp, ts.comparator());
    }

    @Test
    public void testAdd_oneElement_notFull() {
        var ts = new LimitedTreeSet<MyRecord>();

        var record = new MyRecord(2_000_000_000, "abcdef", 9_000_000_000_000_000L);
        ts.add(record);

        Assertions.assertTrue(ts.contains(record));
        Assertions.assertEquals(1, ts.size());
    }

    @Test
    public void testAdd_oneElement_full() {
        var ts = new LimitedTreeSet<>(2);

        ts.add(new MyRecord(2000, "abcdef", 90000L));
        ts.add(new MyRecord(3000, "zyxopq", 91000L));

        var record = new MyRecord(4000, "full_set", 92000L);
        var result = ts.add(record);

        Assertions.assertFalse(result);
        Assertions.assertFalse(ts.contains(record));
        Assertions.assertEquals(2, ts.size());
    }

    @Test
    public void testAdd_fewElements_notFull() {
        var ts = new LimitedTreeSet<>();

        var record1 = new MyRecord(2000, "abcdef", 90000L);
        var record2 = new MyRecord(3000, "zyxopq", 91000L);
        var record3 = new MyRecord(4000, "full_set", 92000L);

        ts.add(record1);
        ts.add(record2);
        ts.add(record3);

        Assertions.assertTrue(ts.contains(record1));
        Assertions.assertTrue(ts.contains(record2));
        Assertions.assertTrue(ts.contains(record3));
        Assertions.assertEquals(3, ts.size());
    }

    @Test
    public void testAdd_fewElements_full() {
        var ts = new LimitedTreeSet<>(2);

        var record1 = new MyRecord(2000, "abcdef", 90000L);
        var record2 = new MyRecord(3000, "zyxopq", 91000L);
        var record3 = new MyRecord(4000, "full_set", 92000L);
        var record4 = new MyRecord(5000, "full_set_2", 92000L);

        ts.add(record1);
        ts.add(record2);

        var result1 = ts.add(record3);
        var result2 = ts.add(record4);

        Assertions.assertFalse(result1);
        Assertions.assertFalse(result2);
        Assertions.assertTrue(ts.contains(record1));
        Assertions.assertTrue(ts.contains(record2));
        Assertions.assertFalse(ts.contains(record3));
        Assertions.assertFalse(ts.contains(record4));
        Assertions.assertEquals(2, ts.size());
    }

    @Test
    public void testIsFull_empty() {
        var ts = new LimitedTreeSet<>();

        Assertions.assertFalse(ts.isFull());
    }

    @Test
    public void testIsFull_notFull() {
        var ts = new LimitedTreeSet<>();

        var record1 = new MyRecord(2000, "abcdef", 90000L);
        var record2 = new MyRecord(3000, "zyxopq", 91000L);

        ts.add(record1);
        ts.add(record2);

        Assertions.assertFalse(ts.isFull());
    }

    @Test
    public void testIsFull_full() {
        var ts = new LimitedTreeSet<>(2);

        var record1 = new MyRecord(2000, "abcdef", 90000L);
        var record2 = new MyRecord(3000, "zyxopq", 91000L);

        ts.add(record1);
        ts.add(record2);

        Assertions.assertTrue(ts.isFull());
    }

    @Test
    public void testSetLimitSize_changeLimitSize() {
        var ts = new LimitedTreeSet<>(30);

        Assertions.assertEquals(30, ts.getSizeLimit());

        ts.setSizeLimit(45);

        Assertions.assertEquals(45, ts.getSizeLimit());
    }

    @Test
    public void testRemove_empty() {
        var ts = new LimitedTreeSet<>();

        var record1 = new MyRecord(2000, "abcdef", 90000L);

        var result = ts.remove(record1);

        Assertions.assertFalse(result);
        Assertions.assertEquals(0, ts.size());
    }

    @Test
    public void testRemove_notExists() {
        var ts = new LimitedTreeSet<>();

        var record1 = new MyRecord(2000, "abcdef", 90000L);
        var record2 = new MyRecord(3000, "zyxopq", 91000L);
        var record3 = new MyRecord(4000, "full_set", 92000L);

        ts.add(record1);
        ts.add(record2);

        var result = ts.remove(record3);

        Assertions.assertFalse(result);
        Assertions.assertEquals(2, ts.size());
    }

    @Test
    public void testRemove_exists() {
        var ts = new LimitedTreeSet<>();

        var record1 = new MyRecord(2000, "abcdef", 90000L);
        var record2 = new MyRecord(3000, "zyxopq", 91000L);

        ts.add(record1);
        ts.add(record2);

        var result = ts.remove(record2);

        Assertions.assertTrue(result);
        Assertions.assertEquals(1, ts.size());
    }

    @Test
    public void testContains_empty() {
        var ts = new LimitedTreeSet<>();

        var record1 = new MyRecord(2000, "abcdef", 90000L);

        var result = ts.contains(record1);

        Assertions.assertFalse(result);
    }

    @Test
    public void testContains_notExists() {
        var ts = new LimitedTreeSet<>();

        var record1 = new MyRecord(2000, "abcdef", 90000L);
        var record2 = new MyRecord(3000, "zyxopq", 91000L);
        var record3 = new MyRecord(4000, "full_set", 92000L);

        ts.add(record1);
        ts.add(record2);

        var result = ts.contains(record3);

        Assertions.assertFalse(result);
    }

    @Test
    public void testContains_exists() {
        var ts = new LimitedTreeSet<>();

        var record1 = new MyRecord(2000, "abcdef", 90000L);
        var record2 = new MyRecord(3000, "zyxopq", 91000L);

        ts.add(record1);
        ts.add(record2);

        var result = ts.contains(record2);

        Assertions.assertTrue(result);
    }

    @Test
    public void testLast() {
        var record1 = new MyRecord(2000, "abcdef", 90000L);
        var record2 = new MyRecord(3000, "zyxopq", 91000L);
        var record3 = new MyRecord(4000, "full_set", 92000L);
        var record4 = new MyRecord(5000, "full_set_2", 93000L);

        var comp = new Comparator<MyRecord>() {
            @Override
            public int compare(MyRecord o1, MyRecord o2) {
                return (int) (o1.c - o2.c);
            }
        };

        var ts = new LimitedTreeSet<>(comp);

        ts.add(record2);
        ts.add(record3);
        ts.add(record1);
        ts.add(record4);

        Assertions.assertEquals(record4, ts.last());
    }

    @Test
    public void testFirst() {
        var record1 = new MyRecord(2000, "abcdef", 90000L);
        var record2 = new MyRecord(3000, "zyxopq", 91000L);
        var record3 = new MyRecord(4000, "full_set", 92000L);
        var record4 = new MyRecord(5000, "full_set_2", 93000L);

        var comp = new Comparator<MyRecord>() {
            @Override
            public int compare(MyRecord o1, MyRecord o2) {
                return (int) (o1.c - o2.c);
            }
        };

        var ts = new LimitedTreeSet<>(comp);

        ts.add(record2);
        ts.add(record3);
        ts.add(record1);
        ts.add(record4);

        Assertions.assertEquals(record1, ts.first());
    }

    private record MyRecord(int a, String b, long c)
            implements Comparable<MyRecord>
    {
        @Override
        public int compareTo(MyRecord o) {
            return (int) (this.c - o.c);
        }
    }

}
