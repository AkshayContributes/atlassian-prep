package org.atlassian.OrganisationHierarchy;

import java.util.List;

public interface OrganisationManager {
    void addDepartment(String deptId, String parentName);

    void addEmployee(String empId, String deptId);

    String findLCA(List<String> employees);
}
