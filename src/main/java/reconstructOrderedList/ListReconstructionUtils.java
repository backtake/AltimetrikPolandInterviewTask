package reconstructOrderedList;

import utils.Pair;

import java.util.*;

public final class ListReconstructionUtils {
    private ListReconstructionUtils() {
    }

    public static List<String> reconstructOrder(List<Pair<String, String>> pairs) {
        List<String> orderedList = new ArrayList<>();
        Map<String, String> nextValues = new HashMap<>();
        Set<String> allNexts = new HashSet<>();

        pairs.forEach(pair -> {
            nextValues.put(pair.key(), pair.value());
            if (pair.value() != null) allNexts.add(pair.value());
        });

        String startKey = null;
        for (String key: nextValues.keySet()) {
            if (!allNexts.contains(key)) {
                startKey = key;
                break;
            }
        };


        buildOrderedList(startKey, nextValues, orderedList);
        return orderedList;
    }

    private static void buildOrderedList(String current, Map<String, String> nextValues, List<String> orderedList) {
        if (current == null) return;
        orderedList.add(current);
        buildOrderedList(nextValues.get(current), nextValues, orderedList);
    }
}
