package reconstructOrderedList;

import org.junit.jupiter.api.Test;
import utils.Pair;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListReconstructionUtilsTest {

    @Test
    void testSimpleChain() {
        List<Pair<String, String>> input = List.of(
                new Pair<>("A", "B"),
                new Pair<>("B", "C"),
                new Pair<>("C", null)
        );
        List<String> expected = List.of("A", "B", "C");

        assertEquals(expected, ListReconstructionUtils.reconstructOrder(input));
    }

    @Test
    void testOutOfOrderPairs() {
        List<Pair<String, String>> input = List.of(
                new Pair<>("C", "D"),
                new Pair<>("A", "B"),
                new Pair<>("B", "C"),
                new Pair<>("D", null)
        );
        List<String> expected = List.of("A", "B", "C", "D");

        assertEquals(expected, ListReconstructionUtils.reconstructOrder(input));
    }

    @Test
    void testSingleElementChain() {
        List<Pair<String, String>> input = List.of(
                new Pair<>("S", null)
        );
        List<String> expected = List.of("S");

        assertEquals(expected, ListReconstructionUtils.reconstructOrder(input));
    }
}