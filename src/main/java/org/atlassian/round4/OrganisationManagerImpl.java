package org.atlassian.round4;

import org.atlassian.round3.exception.InvalidInputParamException;
import org.atlassian.round4.model.Department;
import org.atlassian.round4.model.Employee;

import java.util.*;

public class OrganisationManagerImpl implements OrganisationManager {

    private final Map<String, Department> departmentMap;

    private final Map<String, Employee> employeeMap;

    public OrganisationManagerImpl(Map<String, Department> departmentMap, Map<String, Employee> employeeMap) {
        this.departmentMap = departmentMap;
        this.employeeMap = employeeMap;
        this.departmentMap.put("ROOT", null);
    }

    @Override
    public void addDepartment(String deptId, String parentName) {
        if(parentName == null || !this.departmentMap.containsKey(parentName)) {
            throw new InvalidInputParamException("Given Parent Doesn't Exist");
        }

        if(deptId == null) {
            throw new InvalidInputParamException("Department Id cannot be null");
        }

        Department department = new Department(deptId);
        department.setParent(this.departmentMap.get(parentName));
        departmentMap.put(department.getId(), department);
    }

    @Override
    public void addEmployee(String empId, String deptId) {
        if(deptId == null || !this.departmentMap.containsKey(deptId)) {
            throw new InvalidInputParamException("Parent Can't be null");
        }

        if(empId == null) {
            throw new InvalidInputParamException("EmployeeId can't be null");
        }

        employeeMap.putIfAbsent(empId, new Employee(empId));
        employeeMap.get(empId).setDepartment(this.departmentMap.get(deptId));
    }

    @Override
    public String findLCA(List<String> employees) {
        if(employees == null || employees.isEmpty()) {
            throw new InvalidInputParamException("List of Employees can't be null");
        }

        Set<Department> uniqueDepartments = new HashSet<>();


        for(String empId: employees) {
            if(!this.employeeMap.containsKey(empId)) {
                throw new InvalidInputParamException("Invalid Employee Id");
            }

            Employee emp = this.employeeMap.get(empId);
            uniqueDepartments.add(emp.getDepartment());
        }

        Iterator<Department> it = uniqueDepartments.iterator();
        Department lca = it.next();
        while(it.hasNext()) {
            lca = findLCA(lca, it.next());
            if(lca.getParent() == null) return lca.getId();
        }

        return lca.getId();
    }

    private Department findLCA(Department a, Department b) {
        if(a == null) {
            return b;
        }

        if(b == null) {
            return a;
        }

        while(a.getDepth() > b.getDepth()) {
            a = a.getParent();
        }

        while(b.getDepth() > a.getDepth()) {
            b = b.getParent();
        }

        while(a != b) {
            a = a.getParent();
            b = b.getParent();
        }


        return a;
    }


}
