package org.atlassian.RatingManager.repository;

import org.atlassian.RatingManager.model.Agent;

import java.util.Optional;

public interface AgentRepository {
    Optional<Agent> getById(String agentId);

    void save(Agent agent);

}
