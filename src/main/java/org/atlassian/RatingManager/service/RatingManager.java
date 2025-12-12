package org.atlassian.RatingManager.service;

import org.atlassian.RatingManager.model.Agent;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public interface RatingManager {
    void addRatingForAgent(int i, String agentID, LocalDate date);

    double getRatingForAgent(String agentId);

    List<Agent> getAllAgents();

    Map<YearMonth, Agent> getMonthlyLeaders();
}
