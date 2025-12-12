package org.atlassian.RatingManager.service;

import org.atlassian.RatingManager.exception.ErrorCodes;
import org.atlassian.RatingManager.exception.InvalidAgentIdException;
import org.atlassian.RatingManager.model.Agent;
import org.atlassian.RatingManager.repository.AgentRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RatingManagerImpl implements RatingManager {
    private final AgentRepository agentRepository;

    private final TreeSet<Agent> sortedByRating;
    private final Map<YearMonth, TreeSet<Agent>> monthlyLogs;

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock writeLock = readWriteLock.writeLock();
    private final Lock readLock = readWriteLock.readLock();

    public RatingManagerImpl(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
        this.sortedByRating = getTreeSetSortedByRating();
        this.monthlyLogs = new HashMap<>();

    }

    private static TreeSet<Agent> getTreeSetSortedByRating() {
        return new TreeSet<>((a1, a2) -> {
            if (!a1.getAverageRating().equals(a2.getAverageRating())) {
                return a2.getAverageRating().compareTo(a1.getAverageRating());
            }
            return a1.getId().compareTo(a2.getId());
        });
    }

    private static TreeSet<Agent> getTreeSetSortedByMonthlyRating(YearMonth yearMonth) {
        return new TreeSet<>((a1, a2) -> {
            if (!a1.getAverageRatingByMonth(yearMonth).equals(a2.getAverageRatingByMonth(yearMonth))) {
                return a2.getAverageRatingByMonth(yearMonth).compareTo(a1.getAverageRatingByMonth(yearMonth));
            }
            return a1.getId().compareTo(a2.getId());
        });
    }



    @Override
    public void addRatingForAgent(int rating, String agentId, LocalDate date) {
        writeLock.lock();
        try {
            Agent agent = this.agentRepository.getById(agentId).orElseThrow(() -> new InvalidAgentIdException(ErrorCodes.INVALID_AGENT_ID));
            this.monthlyLogs.putIfAbsent(YearMonth.from(date), getTreeSetSortedByMonthlyRating(YearMonth.from(date)));
            this.monthlyLogs.get(YearMonth.from(date)).remove(agent);
            this.sortedByRating.remove(agent);
            agent.updateRating(rating, date);
            this.sortedByRating.add(agent);
            this.monthlyLogs.get(YearMonth.from(date)).add(agent);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public double getRatingForAgent(String agentId) {
        readLock.lock();
        try {
            Agent agent = this.agentRepository.getById(agentId).orElseThrow(()-> new InvalidAgentIdException(ErrorCodes.INVALID_AGENT_ID));
            return agent.getAverageRating();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public List<Agent> getAllAgents() {
        readLock.lock();
        try {
            return this.sortedByRating.stream().toList();
        } finally {
            readLock.unlock();
        }
    }

    public Map<YearMonth, Agent> getMonthlyLeaders() {
        readLock.lock();
        Map<YearMonth, Agent> result = new HashMap<>();
        try{
            for(YearMonth month: monthlyLogs.keySet()) {
                result.putIfAbsent(month, monthlyLogs.get(month).first());
            }
        }finally {
            readLock.unlock();
        }
        return result;
    }


    public String generateMonthlyReportInCSV() {
        readLock.lock();
        StringBuilder csvBuilder = new StringBuilder();
        try{
            csvBuilder.append("Month,Agent ID,Average Rating\n");

            List<YearMonth> sortedMonths = monthlyLogs.keySet().stream().sorted().toList();

            for(YearMonth month: sortedMonths) {
                List<Agent> agents = monthlyLogs.get(month).stream().toList();
                for(Agent agent: agents) {
                    csvBuilder.append(month.toString());
                    csvBuilder.append(",");
                    csvBuilder.append(agent.getId());
                    csvBuilder.append(",");
                    csvBuilder.append(String.format("%.2f", agent.getAverageRatingByMonth(month)));
                    csvBuilder.append("\n");
                }
            }
        }finally {
            readLock.unlock();
        }
        return csvBuilder.toString();
    }
}
