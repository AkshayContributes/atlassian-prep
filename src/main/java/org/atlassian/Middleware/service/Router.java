package org.atlassian.Middleware.service;

public interface Router {
    void addPathForValue(String path, String value);

    String getValueFromPath(String path);
}
