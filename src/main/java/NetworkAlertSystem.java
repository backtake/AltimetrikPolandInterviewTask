import java.util.*;

public class NetworkAlertSystem implements AlertNetwork {
    private final Map<String, List<String>> serviceDependencies = new HashMap<>();

    public Map<String, List<String>> getServiceDependencies() {
        return serviceDependencies;
    }

    @Override
    public void addService(String service) {
        serviceDependencies.putIfAbsent(service, new ArrayList<>());
    }

    @Override
    public void addDependency(String fromService, String toService) {
        addService(fromService);
        addService(toService);
        getDependencies(fromService).add(toService);
        getDependencies(toService).add(fromService);
    }

    @Override
    public List<String> getDependencies(String service) {
        return serviceDependencies.get(service);
    }

    @Override
    public List<String> findAlertPropagationPath(String source, String target) {
        if (!serviceDependencies.containsKey(source) || !serviceDependencies.containsKey(target)) {
            return List.of();
        }

        Queue<String> queue = new LinkedList<>();
        Set<String> alreadyVisited = new HashSet<>();

        while (!queue.isEmpty()) {
            String currentNode = queue.remove();
            if (currentNode.equals(target)) {
                break;
            }

            serviceDependencies.getOrDefault(currentNode, List.of())
                    .forEach(dependency -> {
                        if (!alreadyVisited.contains(dependency)) {
                            queue.add(dependency);
                            alreadyVisited.add(dependency);
                        }
                    });
        }

        //TODO: jakos cza jeszcze utworzyc patha

        return null;
    }

    @Override
    public List<String> getAffectedServices(String source) {
        return null;
    }

    @Override
    public List<Pair<String, String>> suggestContainmentEdges(String source) {
        return null;
    }
}
