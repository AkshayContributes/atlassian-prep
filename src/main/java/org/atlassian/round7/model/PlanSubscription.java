package org.atlassian.round7.model;

import java.time.LocalDate;

public class PlanSubscription {
    private final String customerId;
    private final LocalDate startDate;
    private final Product product;
    private final Plan plan;

    public PlanSubscription(String customerId, LocalDate startDate, Product product, Plan plan) {
        this.customerId = customerId;
        this.startDate = startDate;
        this.product = product;
        this.plan = plan;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public Product getProduct() {
        return this.product;
    }

    public Plan getPlan() {
        return this.plan;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }
}
