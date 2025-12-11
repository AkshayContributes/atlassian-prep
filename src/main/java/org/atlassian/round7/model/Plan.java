package org.atlassian.round7.model;

public class Plan {
    private final String name;
    private Double price;

    public Plan(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }
}
