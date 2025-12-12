package org.atlassian.RatingManager;

import org.atlassian.RatingManager.model.Agent;
import org.atlassian.RatingManager.repository.AgentRepository;
import org.atlassian.RatingManager.repository.AgentRepositoryImpl;
import org.atlassian.RatingManager.service.RatingManager;
import org.atlassian.RatingManager.service.RatingManagerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RatingManagerTest {
    RatingManager ratingManager;

    @BeforeEach
    public void setup() {
        AgentRepository agentRepository = new AgentRepositoryImpl(new HashMap<>());
        ratingManager = new RatingManagerImpl(agentRepository);
        for(int i = 1; i<=10; i++) {
            agentRepository.save(new Agent(String.format("agent_%s", i)));
        }
    }

    @Test
    void AddRatingForAgent_ValidRatingAndAgentId_DoesNotThrow() {
        assertDoesNotThrow(() -> ratingManager.addRatingForAgent(1, "agent_1", LocalDate.of(2022, 1, 10)));
    }

    @Test
    void GetRatingForAgent_ValidAgentId_ReturnsCorrectAverageRating(){
        assertDoesNotThrow(() -> ratingManager.addRatingForAgent(1, "agent_1", LocalDate.of(2022, 1, 10)));
        assertEquals(4.0, ratingManager.getRatingForAgent("agent_1"));
    }

    @Test
    void GetAllAgents_ReturnsAgentsOrderedByRating() {
        assertDoesNotThrow(() -> ratingManager.addRatingForAgent(4, "agent_1", LocalDate.of(2022, 1, 10)));
        assertDoesNotThrow(() -> ratingManager.addRatingForAgent(5, "agent_1", LocalDate.of(2022, 1, 20)));
        assertDoesNotThrow(() -> ratingManager.addRatingForAgent(3, "agent_1", LocalDate.of(2022, 2, 10)));
        assertDoesNotThrow(() -> ratingManager.addRatingForAgent(3, "agent_2", LocalDate.of(2022, 1, 25)));
        assertDoesNotThrow(() -> ratingManager.addRatingForAgent(5, "agent_2", LocalDate.of(2022, 2, 25)));
        assertDoesNotThrow(() -> ratingManager.addRatingForAgent(2, "agent_3", LocalDate.of(2022, 3, 25)));

        assertEquals(3, ratingManager.getAllAgents().size());
        assertEquals(3, ratingManager.getMonthlyLeaders().size());
        assertEquals("agent_2", ratingManager.getMonthlyLeaders().get(YearMonth.of(2022, 2)).getId());



    }
}
