package org.atlassian.CostExplorer;

import org.atlassian.Snake.exception.InvalidInputParamException;
import org.atlassian.CostExplorer.model.Plan;
import org.atlassian.CostExplorer.model.PlanSubscription;
import org.atlassian.CostExplorer.model.Product;
import org.atlassian.CostExplorer.repository.ProductRepository;
import org.atlassian.CostExplorer.repository.SubscriptionRepository;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

public class CostExplorerImpl implements CostExplorer {

    private final ProductRepository productRepo;
    private final SubscriptionRepository subscriptionRepo;

    public CostExplorerImpl(ProductRepository productRepo, SubscriptionRepository subscriptionRepo) {
        this.productRepo = productRepo;
        this.subscriptionRepo = subscriptionRepo;
    }

    @Override
    public void addProduct(String name, List<String> planStrings) {
        Set<Plan> plans = planStrings.stream().map(planStr -> {
            String[] planDetails = planStr.split(",");
            return new Plan(planDetails[0], Double.parseDouble(planDetails[1]));
        }).collect(Collectors.toSet());

        Map<String, Plan> nameToPlan = new HashMap<>();
        for(Plan plan: plans) {
            nameToPlan.put(plan.getName(), plan);
        }

        Product product = new Product(name, nameToPlan);
        this.productRepo.save(product);
    }

    @Override
    public void subscribe(String customerId, LocalDate startDate, String productName, String planName) {

        Product product = this.productRepo.getByName(productName).orElseThrow(() -> new InvalidInputParamException("Invalid Product Id"));
        Plan plan = (Optional.ofNullable(product.getPlans().get(planName)).orElseThrow(() -> new InvalidInputParamException("Invalid Plan Name")));
        PlanSubscription planSubscription = new PlanSubscription(customerId, startDate, product, plan);
        this.subscriptionRepo.save(planSubscription);
    }

    @Override
    public Map<YearMonth, Double> getMonthlyCosts(String customerId, Year year) {
        List<PlanSubscription> subscriptionsForCustomer = this.subscriptionRepo.getSubscriptionsForCustomer(customerId);
        Map<YearMonth, Double> monthlyCosts = new TreeMap<>();

        for(PlanSubscription planSubscription: subscriptionsForCustomer) {
            LocalDate startDate = planSubscription.getStartDate();
            YearMonth startYM = YearMonth.of(startDate.getYear(), startDate.getMonth());
            double monthlyPrice = planSubscription.getPlan().getPrice();
            for(int month = 1; month <= 12; month++) {
                YearMonth currentYM = YearMonth.of(year.getValue(), month);

                if(currentYM.isBefore(startYM)) {
                    continue;
                }

                if(currentYM.equals(startYM)) {
                    int lengthOfMonth = startYM.lengthOfMonth();
                    int daysActive = lengthOfMonth - startDate.getDayOfMonth() + 1;
                    double proRated = (monthlyPrice / lengthOfMonth) * daysActive;
                    monthlyCosts.merge(currentYM, proRated, Double::sum);
                }

                else {
                    monthlyCosts.merge(currentYM, monthlyPrice, Double::sum);
                }
            }

        }

        return monthlyCosts;

    }

    @Override
    public double getAnnualCost(String customerId, Year year) {
        return getMonthlyCosts(customerId, year).values().stream().mapToDouble(d->d).sum();
    }


}
