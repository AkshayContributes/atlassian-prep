package org.atlassian.round2.repository;

import org.atlassian.round1.repository.DefaultRouteRepositoryImpl;
import org.atlassian.round2.model.Agent;

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
