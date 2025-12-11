package org.atlassian.round2.service;

import org.atlassian.round2.model.Agent;

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
