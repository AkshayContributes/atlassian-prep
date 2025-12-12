package org.atlassian.LRUCache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LRUCacheWithTTL<K, V> implements LRUCache<K, V> {

    private final Map<K, Node<K, V>> cache;
    private final Node<K, V> head;
    private final Node<K, V> tail;
    private final int capacity;

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock writeLock = readWriteLock.writeLock();
    private final Lock readLock = readWriteLock.readLock();

    public LRUCacheWithTTL(int capacity) {
        this.cache = new HashMap<>();
        this.head = new Node<>(null, null, Integer.MAX_VALUE);
        this.tail = new Node<>(null, null, Integer.MAX_VALUE);
        this.head.next = tail;
        this.tail.prev = head;
        this.capacity = capacity;

    }

    @Override
    public void put(K key, V value, long ttlInMillis) {
        writeLock.lock();
        try {
            long expireAtMillis = System.currentTimeMillis() + ttlInMillis;
            if(cache.containsKey(key)) {
                Node<K, V> node = cache.get(key);
                node.expireAtMillis = System.currentTimeMillis() + ttlInMillis;
                node.value = value;
                removeNode(node);
                placeAtHead(node);
                cache.put(key, node);
            }else {
                if(size() >= capacity) {
                    cache.remove(evictFromTail());
                }
                Node<K, V> node = new Node<>(key, value, expireAtMillis);
                placeAtHead(node);
                cache.put(key, node);

            }
            System.out.println("UPDATED CACHE");
        }  finally {
            writeLock.unlock();
        }
    }

    private K evictFromTail() {
        Node<K, V> prevNode = tail.prev;
        prevNode.prev.next = tail;
        tail.prev = prevNode.prev;
        prevNode.next = null;
        return prevNode.key;
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public V get(K key) {
        writeLock.lock();
        try {
            if(!cache.containsKey(key)) return null;

            Node<K, V> node = cache.get(key);

            long now = System.currentTimeMillis();

            if(now >= node.expireAtMillis) {
                System.out.println("Key Expired Evicting");
                cache.remove(key);
                removeNode(node);
                return null;
            }

            removeNode(node);
            placeAtHead(node);
            return node.value;
        } finally {
            writeLock.unlock();
        }
    }


    private void placeAtHead(Node<K,V> node) {
        Node<K, V> nextNode = head.next;
        nextNode.prev = node;
        node.next = nextNode;
        node.prev = head;
        head.next = node;
    }


    private void removeNode(Node<K,V> node) {
        Node<K, V> prevNode = node.prev;
        Node<K, V> nextNode = node.next;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
    }




    private static class Node<K, V> {
        final K key;
        V value;
        Node<K,V> next;
        Node<K, V> prev;
        long expireAtMillis;

        private Node(K key, V value, long expireAtMillis) {
            this.key = key;
            this.value = value;
            this.expireAtMillis = expireAtMillis;
            next = null;
            prev = null;
        }
    }
}
