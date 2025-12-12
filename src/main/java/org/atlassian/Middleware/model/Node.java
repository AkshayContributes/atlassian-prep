package org.atlassian.Middleware.model;

import java.util.Map;

public class Node {
    private String value;
    private final Map<String, Node> children;

    public Node(Map<String, Node> children) {
        this.children = children;
    }

    public Map<String, Node> getChildren() {
        return this.children;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
