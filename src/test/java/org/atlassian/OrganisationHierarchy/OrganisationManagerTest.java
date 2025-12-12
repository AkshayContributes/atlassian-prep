package org.atlassian.OrganisationHierarchy;

import org.atlassian.Snake.exception.InvalidInputParamException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrganisationManagerTest {
    OrganisationManager organisationManager;

    @BeforeEach
    public void setup() {
        organisationManager = new OrganisationManagerImpl(new HashMap<>(), new HashMap<>());
    }

    @Test
    void AddDepartment_ValidParent_DoesNotThrow() {
        assertDoesNotThrow(() -> organisationManager.addDepartment("D1", "ROOT"));
    }

    @Test
    void AddDepartment_InvalidDepartmentAndEmployee_ThrowsInvalidInputParamException() {
        assertThrows(InvalidInputParamException.class,() -> organisationManager.addDepartment("D1", null));
        assertThrows(InvalidInputParamException.class,() -> organisationManager.addDepartment("D1", "D2"));

    }

    @Test
    void AddEmployee_ValidDepartment_DoesNotThrow() {
        organisationManager.addDepartment("D1", "ROOT");
        assertDoesNotThrow(() -> organisationManager.addEmployee("E1", "D1"));
    }

    @Test
    void FindLCA_ValidDepartments_ReturnsLCA() {
        organisationManager.addDepartment("D1", "ROOT");
        organisationManager.addDepartment("D2", "D1");
        organisationManager.addDepartment("D3", "D1");
        organisationManager.addDepartment("D4", "D2");

        organisationManager.addEmployee("E1", "D1");
        organisationManager.addEmployee("E2", "D2");
        organisationManager.addEmployee("E3", "D3");
        organisationManager.addEmployee("E4", "D4");

        assertEquals("D1", organisationManager.findLCA(List.of("E2", "E3")));
        assertEquals("D2", organisationManager.findLCA(List.of("E2", "E4")));






    }






}
