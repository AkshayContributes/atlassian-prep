package org.atlassian.RateLimiter;

import org.atlassian.Snake.exception.InvalidInputParamException;
import org.atlassian.RateLimiter.strategy.RateLimiter;
import org.atlassian.RateLimiter.strategy.RateLimiterFactory;

import java.util.Map;

public class RateLimiterServiceImpl implements RateLimiterService {


    private final Map<String, RateLimiter> rateLimiterForResource;
    private final RateLimiterFactory rateLimiterFactory;

    public RateLimiterServiceImpl(Map<String, RateLimiter> rateLimiterForResource, RateLimiterFactory rateLimiterFactory) {
        this.rateLimiterForResource = rateLimiterForResource;
        this.rateLimiterFactory = rateLimiterFactory;
    }

    @Override
    public void addResource(String resource, String rateLimiterType, String limits) {

        try{
            String[] limitDetails = limits.split(",");
            int hitLimit = Integer.parseInt(limitDetails[0]);
            int timeWindow = Integer.parseInt(limitDetails[1]);
            this.rateLimiterForResource.put(resource, this.rateLimiterFactory.get(rateLimiterType, hitLimit, timeWindow));
        }catch (NumberFormatException e) {
            throw new InvalidInputParamException("Invalid Limits Provided");
        }
    }

    @Override
    public boolean isAllowed(String resource, int timestamp) {
        if(!this.rateLimiterForResource.containsKey(resource)) {
            throw new InvalidInputParamException("Invalid resource");
        }

        return this.rateLimiterForResource.get(resource).isAllowed(timestamp);
    }
}
