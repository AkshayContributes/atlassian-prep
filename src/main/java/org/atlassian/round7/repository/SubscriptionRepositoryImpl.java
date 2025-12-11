package org.atlassian.round7.repository;

import org.atlassian.round7.model.Plan;
import org.atlassian.round7.model.PlanSubscription;
import org.atlassian.round7.model.Product;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscriptionRepositoryImpl implements SubscriptionRepository {
    private final Map<String, Map<Product, PlanSubscription>> subscriptions;

    public SubscriptionRepositoryImpl(HashMap<String, Map<Product, PlanSubscription>> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    public void save(PlanSubscription planSubscription) {
        this.subscriptions.putIfAbsent(planSubscription.getCustomerId(), new HashMap<>());
        this.subscriptions.get(planSubscription.getCustomerId()).put(planSubscription.getProduct(), planSubscription);
    }

    @Override
    public List<PlanSubscription> getSubscriptionsForCustomer(String customerId) {
        if(!this.subscriptions.containsKey(customerId)) {
            return List.of();
        }

        Map<Product, PlanSubscription> subscriptionsForCustomer = this.subscriptions.get(customerId);

        return subscriptionsForCustomer.values().stream().toList();
    }
}
