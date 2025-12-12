package org.atlassian.CostExplorer.model;

import java.util.Map;

public class Product {

    private final String name;
    private final Map<String, Plan> plans;

    public Product(String name, Map<String, Plan> plans) {
        this.name = name;
        this.plans = plans;
    }


    public String getName() {
        return this.name;
    }

    public Map<String, Plan> getPlans() {
        return this.plans;
    }
}
