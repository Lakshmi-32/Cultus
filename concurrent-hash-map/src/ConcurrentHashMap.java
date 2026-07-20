import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrentHashMap<K, V> {

    private static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private List<LinkedList<Entry<K, V>>> buckets;

    private int size;

    private final double loadFactor;

    private final ReentrantReadWriteLock lock =
            new ReentrantReadWriteLock();

    private static final int DEFAULT_CAPACITY = 16;

    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    public ConcurrentHashMap() {
        this(
                DEFAULT_CAPACITY,
                DEFAULT_LOAD_FACTOR
        );
    }

    public ConcurrentHashMap(
            int capacity,
            double loadFactor
    ) {

        if (capacity <= 0) {
            throw new IllegalArgumentException(
                    "Capacity must be greater than zero"
            );
        }

        if (
                loadFactor <= 0 ||
                loadFactor >= 1
        ) {
            throw new IllegalArgumentException(
                    "Load factor must be between 0 and 1"
            );
        }

        this.loadFactor = loadFactor;

        buckets = new ArrayList<>();

        for (
                int i = 0;
                i < capacity;
                i++
        ) {
            buckets.add(
                    new LinkedList<>()
            );
        }
    }

    private int getIndex(K key) {

        int hash =
                key.hashCode();

        hash =
                hash ^ (hash >>> 16);

        return Math.abs(
                hash % buckets.size()
        );
    }

    public void put(K key, V value) {

        lock.writeLock().lock();

        try {

            int index =
                    getIndex(key);

            LinkedList<Entry<K, V>> bucket =
                    buckets.get(index);

            for (
                    Entry<K, V> entry :
                    bucket
            ) {

                if (
                        entry.key.equals(key)
                ) {

                    entry.value =
                            value;

                    return;
                }
            }

            bucket.add(
                    new Entry<>(
                            key,
                            value
                    )
            );

            size++;

            if (
                    (double) size /
                    buckets.size()
                    > loadFactor
            ) {

                resize();
            }

        } finally {

            lock.writeLock().unlock();
        }
    }

    public V get(K key) {

        lock.readLock().lock();

        try {

            int index =
                    getIndex(key);

            LinkedList<Entry<K, V>> bucket =
                    buckets.get(index);

            for (
                    Entry<K, V> entry :
                    bucket
            ) {

                if (
                        entry.key.equals(key)
                ) {

                    return entry.value;
                }
            }

            return null;

        } finally {

            lock.readLock().unlock();
        }
    }

    public V remove(K key) {

        lock.writeLock().lock();

        try {

            int index =
                    getIndex(key);

            LinkedList<Entry<K, V>> bucket =
                    buckets.get(index);

            for (
                    Entry<K, V> entry :
                    bucket
            ) {

                if (
                        entry.key.equals(key)
                ) {

                    V value =
                            entry.value;

                    bucket.remove(entry);

                    size--;

                    return value;
                }
            }

            return null;

        } finally {

            lock.writeLock().unlock();
        }
    }

    private void resize() {

        int newCapacity =
                buckets.size() * 2;

        List<LinkedList<Entry<K, V>>>
                oldBuckets =
                buckets;

        buckets =
                new ArrayList<>();

        for (
                int i = 0;
                i < newCapacity;
                i++
        ) {

            buckets.add(
                    new LinkedList<>()
            );
        }

        for (
                LinkedList<Entry<K, V>> bucket :
                oldBuckets
        ) {

            for (
                    Entry<K, V> entry :
                    bucket
            ) {

                int index =
                        getIndex(entry.key);

                buckets
                        .get(index)
                        .add(entry);
            }
        }
    }

    public int size() {

        lock.readLock().lock();

        try {

            return size;

        } finally {

            lock.readLock().unlock();
        }
    }

    public int capacity() {

        lock.readLock().lock();

        try {

            return buckets.size();

        } finally {

            lock.readLock().unlock();
        }
    }

    public double getLoadFactor() {

        lock.readLock().lock();

        try {

            return (double) size /
                    buckets.size();

        } finally {

            lock.readLock().unlock();
        }
    }
}