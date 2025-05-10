import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NetworkAlertSystemTest {
    private AlertNetwork network;

    @BeforeEach
    void setUp() {
        network = new NetworkAlertSystem();
        network.addService("A");
        network.addService("B");
        network.addService("C");
        network.addService("D");

        network.addDependency("A", "B");
        network.addDependency("B", "C");
        network.addDependency("A", "D");
        network.addDependency("D", "C");
    }

    @Test
    void testFindAlertPropagationPath() {
        List<String> path = network.findAlertPropagationPath("A", "C");

        assertFalse(path.isEmpty(), "Path should not be empty");
        assertEquals("A", path.getFirst(), "Path should start from source");
        assertEquals("C", path.getLast(), "Path should end at target");

        List<String> validPath1 = List.of("A", "B", "C");
        assertEquals(path, validPath1, "Path should be the shortest paths");
    }

    @Test
    void testFindAlertPropagationPath_NoPath() {
        network.addService("E");
        List<String> path = network.findAlertPropagationPath("A", "E");
        assertTrue(path.isEmpty(), "No path should return an empty list");
    }

    @Test
    void testFindAlertPropagationPath_UnknownPath() {
        List<String> path = network.findAlertPropagationPath("A", "E");
        assertTrue(path.isEmpty(), "No path should return an empty list");
    }

    @Test
    void testGetAffectedServices() {
        Set<String> affected = network.getAffectedServices("A");

        Set<String> expected = Set.of("D", "C", "B");
        assertEquals(expected.size(), affected.size());
        assertTrue(affected.containsAll(expected), "Affected services should include D, C, and B");
    }

    @Test
    void testGetAffectedServices_NoDependencies() {
        network.addService("E");
        Set<String> affected = network.getAffectedServices("E");
        assertTrue(affected.isEmpty(), "No downstream services should result in empty list");
    }

    @Test
    void testSuggestContainmentEdges() {
        List<Pair<String, String>> suggestions = network.suggestContainmentEdges("A");

        assertFalse(suggestions.isEmpty(), "Suggestions should not be empty");

        // Best containment: cutting A → B AND A → D
        boolean valid = suggestions.contains(new Pair<>("A", "B")) && suggestions.contains(new Pair<>("A", "D"));
        assertTrue(valid, "Containment suggestions should ideally cut edges from A to prevent propagation entirely");
    }
}
