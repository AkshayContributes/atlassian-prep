package org.atlassian.round2.repository;

import org.atlassian.round2.model.Agent;

import java.util.Optional;

public interface AgentRepository {
    Optional<Agent> getById(String agentId);

    void save(Agent agent);

}
