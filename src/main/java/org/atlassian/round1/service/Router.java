package org.atlassian.round1.service;

public interface Router {
    void addPathForValue(String path, String value);

    String getValueFromPath(String path);
}
