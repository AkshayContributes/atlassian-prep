package org.atlassian.round1.repository;

import java.util.Map;
import java.util.Optional;

public class DefaultRouteRepositoryImpl implements RouteRepository {
    private final Map<String, String> routes;

    public DefaultRouteRepositoryImpl(Map<String, String> routes) {
        this.routes = routes;
    }

    @Override
    public void save(String path, String value) {
        this.routes.put(path, value);
    }

    @Override
    public Optional<String> get(String path) {
        return Optional.ofNullable(this.routes.get(path));
    }
}
