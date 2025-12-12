package org.atlassian.round9;

public interface LRUCache<K, V> {
    void put(K key, V value, long ttlInMillis);

    int size();

    V get(K key);
}
