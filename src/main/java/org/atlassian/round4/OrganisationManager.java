package org.atlassian.round4;

import org.atlassian.round4.model.Department;

import java.util.List;

public interface OrganisationManager {
    void addDepartment(String deptId, String parentName);

    void addEmployee(String empId, String deptId);

    String findLCA(List<String> employees);
}
