package org.atlassian.round1;

import org.atlassian.round1.exceptions.InvalidPathException;
import org.atlassian.round1.model.Node;
import org.atlassian.round1.repository.WildCardRouteRepositoryImpl;
import org.atlassian.round1.service.Router;
import org.atlassian.round1.service.RouterImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

public class RouterTest {
    Router router;

    @BeforeEach
    public void setup() {
        router = new RouterImpl(new WildCardRouteRepositoryImpl(new Node(new LinkedHashMap<>())));
    }

    @Test
    void AddPathForValue_ValidPathAndValue_DoesNotThrow() {
        assertDoesNotThrow(() -> router.addPathForValue("/a/b/c", "value1"));
    }

    @Test
    void GetValueFromPath_ValidPath_ReturnsSavedValue() {
        router.addPathForValue("/a/b/c", "value1");
        assertEquals("value1", router.getValueFromPath("/a/b/c"));
    }

    @Test
    void GetValueFromPath_InvalidPath_ThrowsInvalidPathException() {
        assertThrows(InvalidPathException.class, ()->router.getValueFromPath("/a/b/c"));
    }

    @Test
    void GetValueFromPath_ValidWildcardPath_ReturnsMatchingValue() {
        router.addPathForValue("/a/f/k", "value1");
        router.addPathForValue("/a/d/c", "value2");
//        assertEquals("value1", router.getValueFromPath("/a/t/c"));
//        assertEquals("value1", router.getValueFromPath("/a/b/c"));
        assertEquals("value1", router.getValueFromPath("/a/*/k"));
//        assertThrows(InvalidPathException.class, () -> router.getValueFromPath("/a/d/d"));
    }

    @Test
    void GetValueFromPath_InvalidWildCardPath_ThrowsInvalidPathException() {
        router.addPathForValue("/a/b/c", "value1");
        assertThrows(InvalidPathException.class, () -> router.getValueFromPath("/a/*/d"));
    }

    @Test
    void GetValueFromPath_InValidWildcardPathInAdd_ThrowsInvalidPathException() {
        router.addPathForValue("/a/*/c", "value1");
        router.addPathForValue("/a/d/c", "value2");
        assertThrows(InvalidPathException.class, () ->router.getValueFromPath("/a/b/c"));
    }
}
