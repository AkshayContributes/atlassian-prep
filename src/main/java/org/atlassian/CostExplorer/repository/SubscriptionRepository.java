package org.atlassian.CostExplorer.repository;

import org.atlassian.CostExplorer.model.PlanSubscription;
import java.util.List;

public interface SubscriptionRepository {
    void save(PlanSubscription planSubscription);

    List<PlanSubscription> getSubscriptionsForCustomer(String customerId);
}
