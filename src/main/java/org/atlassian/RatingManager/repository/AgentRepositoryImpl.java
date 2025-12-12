package org.atlassian.RatingManager.repository;

import org.atlassian.RatingManager.model.Agent;

import java.util.Map;
import java.util.Optional;

public class AgentRepositoryImpl implements AgentRepository {
    private final Map<String, Agent> agents;

    public AgentRepositoryImpl(Map<String, Agent> agents) {
        this.agents = agents;
    }

    @Override
    public Optional<Agent> getById(String agentId) {
        return Optional.ofNullable(this.agents.get(agentId));
    }

    @Override
    public void save(Agent agent) {
        this.agents.putIfAbsent(agent.getId(), agent);
    }
}
