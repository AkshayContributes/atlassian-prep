package org.atlassian.Middleware.repository;

import java.util.Optional;

public interface RouteRepository {

    void save(String path, String value);

    Optional<String> get(String path);
}
