package org.atlassian.round7.repository;

import org.atlassian.round7.model.PlanSubscription;
import java.util.List;

public interface SubscriptionRepository {
    void save(PlanSubscription planSubscription);

    List<PlanSubscription> getSubscriptionsForCustomer(String customerId);
}
