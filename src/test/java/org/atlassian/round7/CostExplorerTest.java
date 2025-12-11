package org.atlassian.round7;

import org.atlassian.round7.repository.ProductRepositoryImpl;
import org.atlassian.round7.repository.SubscriptionRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CostExplorerTest {

    CostExplorer costExplorer;

    @BeforeEach
    public void setup() {
        costExplorer = new CostExplorerImpl(new ProductRepositoryImpl(new HashMap<>()), new SubscriptionRepositoryImpl(new HashMap<>()));
    }

    @Test
    void AddProduct_ValidProductAndPlans_DoesNotThrow() {
        assertDoesNotThrow(()->costExplorer.addProduct("JIRA", List.of("BASIC,10")));
    }

    @Test
    void Subscribe_ValidCustomerIdStartDateProductNamePlanId_DoesNotThrow() {
        costExplorer.addProduct("JIRA", List.of("BASIC,10"));
        assertDoesNotThrow(()->costExplorer.subscribe("customer_1", LocalDate.of(2025, 5, 12), "JIRA", "BASIC"));
    }

    @Test
    void GetMonthlyCosts_ValidCustomerId_ReturnsMonthlyCosts() {
        costExplorer.addProduct("jira", List.of("BASIC,50", "PREMIUM,120"));
        costExplorer.addProduct("confluence", List.of("STANDARD,80"));

        assertDoesNotThrow(()->costExplorer.subscribe("customer_1", LocalDate.of(2025, 1, 5), "jira", "BASIC"));
        assertDoesNotThrow(()->costExplorer.subscribe("customer_1", LocalDate.of(2025, 7, 10), "confluence", "STANDARD"));

        assertEquals(12, costExplorer.getMonthlyCosts("customer_1", Year.of(2025)).size());
        assertEquals(130.0, costExplorer.getMonthlyCosts("customer_1", Year.of(2025)).get(YearMonth.of(2025, 8)));
    }


    @Test
    void GetTotalAnnualCost_ValidCustomerId_ReturnsAnnualCost() {
        costExplorer.addProduct("jira", List.of("BASIC,50", "PREMIUM,120"));
        costExplorer.addProduct("confluence", List.of("STANDARD,80"));
        double epsilon = 0.000_001d;

        assertDoesNotThrow(()->costExplorer.subscribe("customer_1", LocalDate.of(2025, 1, 5), "jira", "BASIC"));
        assertDoesNotThrow(()->costExplorer.subscribe("customer_1", LocalDate.of(2025, 7, 10), "confluence", "STANDARD"));
        assertEquals(1050.322580, costExplorer.getAnnualCost("customer_1", Year.of(2025)), epsilon);

    }

}
