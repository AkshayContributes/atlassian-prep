package org.atlassian.CostExplorer;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public interface CostExplorer {
    void addProduct(String product, List<String> plans);

    void subscribe(String customerId, LocalDate startDate, String product, String plan);

    Map<YearMonth, Double> getMonthlyCosts(String customerId, Year year);

    double getAnnualCost(String customerId, Year of);
}
