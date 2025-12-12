package org.atlassian.OrganisationHierarchy.model;

public class Department  {
    private final String id;
    private Department parent;
    private int depth;

    public Department(String id) {
        this.id = id;
        this.depth = 0;
    }

    public void setParent(Department parent) {
        this.parent = parent;
        this.depth = parent == null ? 0 : parent.depth + 1;
    }

    public String getId() {
        return this.id;
    }

    public Department getParent() {
        return parent;
    }

    public int getDepth() {
        return depth;
    }
}
