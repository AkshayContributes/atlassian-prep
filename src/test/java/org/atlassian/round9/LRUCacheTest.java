package org.atlassian.round9;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class LRUCacheTest {
    LRUCache<String, String> lruCache;

    @BeforeEach
    public void setup() {
        this.lruCache = new LRUCacheWithTTL<String, String>(3);
    }

    @Test
    void Put_StringKeyValue_DoesNotThrow() {
        assertDoesNotThrow(()->lruCache.put("Key1", "Value1", 1));
        assertDoesNotThrow(()->lruCache.put("Key2", "Value2", 1));
        assertDoesNotThrow(()->lruCache.put("Key3", "Value3", 1));
    }

    @Test
    void Put_StringKeyValue_SizeShouldNotExceedCapacity() {
        assertDoesNotThrow(()->lruCache.put("Key1", "Value1", 10000));
        assertDoesNotThrow(()->lruCache.put("Key2", "Value2", 20000));
        assertDoesNotThrow(()->lruCache.put("Key3", "Value3", 100000));
        assertDoesNotThrow(()->lruCache.put("Key4", "Value4", 50000));
        assertEquals(3, lruCache.size());

    }

    @Test
    void Get_ValidStringKey_CacheUpdated() {
        assertDoesNotThrow(()->lruCache.put("Key1", "Value1", 100000000));
        assertDoesNotThrow(()->lruCache.put("Key2", "Value2", 20000));
        assertDoesNotThrow(()->lruCache.put("Key3", "Value3", 100000));
        assertEquals("Value1", lruCache.get("Key1"));
        assertDoesNotThrow(()->lruCache.put("Key4", "Value4", 50000));
        assertEquals("Value1", lruCache.get("Key1"));
        assertNull(lruCache.get("Key2"));
    }
}

