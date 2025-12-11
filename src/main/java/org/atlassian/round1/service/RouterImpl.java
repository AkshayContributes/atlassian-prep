package org.atlassian.round1.service;

import org.atlassian.round1.exceptions.ErrorCodes;
import org.atlassian.round1.exceptions.InvalidPathException;
import org.atlassian.round1.repository.RouteRepository;

public class RouterImpl implements Router {

    private final RouteRepository routeRepository;

    public RouterImpl(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Override
    public void addPathForValue(String path, String value) {
        this.routeRepository.save(path, value);

    }

    @Override
    public String getValueFromPath(String path) {
        return this.routeRepository.get(path).orElseThrow(() -> new InvalidPathException(ErrorCodes.INVALID_PATH));
    }
}
