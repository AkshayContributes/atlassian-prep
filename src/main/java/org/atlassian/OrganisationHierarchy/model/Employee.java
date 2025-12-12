package org.atlassian.OrganisationHierarchy.model;

public class Employee {
    private final String id;
    private Department department;

    public Employee(String id) {
        this.id = id;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Department getDepartment() {
        return this.department;
    }
}
