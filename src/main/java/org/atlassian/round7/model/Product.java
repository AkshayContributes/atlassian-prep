package org.atlassian.round7.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
