package org.atlassian.round1.repository;

import org.atlassian.round1.model.Node;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class WildCardRouteRepositoryImpl implements RouteRepository {
    private final Node root;

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock writeLock = readWriteLock.writeLock();
    private final Lock readLock = readWriteLock.readLock();

    public WildCardRouteRepositoryImpl(Node root) {
        this.root = root;
    }

    @Override
    public void save(String path, String value) {

        writeLock.lock();
        try {
           Node node = root;
           List<String> parts = getParts(path);
           for(String part: parts) {
               node = node.getChildren().computeIfAbsent(part, k-> new Node(new HashMap<>()));
           }
           node.setValue(value);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Optional<String> get(String path) {
        readLock.lock();
        try {
            List<String> partsOfPath = getParts(path);
            return findValueRecursiveIfWildCardInSearch(this.root, partsOfPath, 0);
        } finally {
            readLock.unlock();
        }
    }

    private Optional<String> findValueRecursiveIfWildCardInSearch(Node node, List<String> partsOfPath, int index) {
        if(index == partsOfPath.size()) return Optional.ofNullable(node.getValue());

        String part = partsOfPath.get(index);

        if(part.equals("*")) {
            for(Node child : node.getChildren().values()) {
                Optional<String> result = findValueRecursiveIfWildCardInSearch(child, partsOfPath, index+1);
                if(result.isPresent()) return result;
            }
        }else if(node.getChildren().containsKey(part)) {
            Optional<String> result = findValueRecursiveIfWildCardInSearch(node.getChildren().get(part), partsOfPath, index+1);
            return result;
        }

        return Optional.empty();

    }


    private Optional<String> findValueRecursiveIfWildCardInTrie(Node node, List<String> partsOfPath, int index) {
        if(index == partsOfPath.size()) return Optional.ofNullable(node.getValue());

        String part = partsOfPath.get(index);
        if(node.getChildren().containsKey(part)) {
            Node child = node.getChildren().get(part);
            Optional<String> result = findValueRecursiveIfWildCardInTrie(child, partsOfPath, index+1);
            if(result.isPresent()) return result;
        }

        if(node.getChildren().containsKey("*")) {
            Node child = node.getChildren().get("*");
            Optional<String> result = findValueRecursiveIfWildCardInTrie(child, partsOfPath, index+1);
            if(result.isPresent()) return result;
        }

        return Optional.empty();

    }

    private static List<String> getParts(String path) {
        return Arrays.stream(path.split("/")).filter(k -> !k.isEmpty()).toList();
    }
}
