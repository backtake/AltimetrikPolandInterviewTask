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
        Map<String, String> parent = new HashMap<>();

        queue.offer(source);
        parent.put(source, null);

        while (!queue.isEmpty()) {
            String currentNode = queue.poll();

            if (currentNode.equals(target)) {
                List<String> path = new ArrayList<>();
                for (String str = target; str != null; str = parent.get(str)) {
                    path.add(str);
                }
                Collections.reverse(path);
                return path;
            }

            serviceDependencies.getOrDefault(currentNode, List.of())
                    .forEach(dependency -> {
                        if (!parent.containsKey(dependency)) {
                            queue.add(dependency);
                            parent.put(dependency, currentNode);
                        }
                    });
        }

        return List.of();
    }

    @Override
    public Set<String> getAffectedServices(String source) {
        Set<String> visited = new HashSet<>();
        Stack<String> stack = new Stack<>();
        stack.push(source);

        while(!stack.isEmpty()) {
            String currentNode = stack.pop();

            if (visited.add(currentNode)) {
                getDependencies(currentNode).forEach(stack::push);
            }
        }

        visited.remove(source);
        return visited;
    }

    @Override
    public List<Pair<String, String>> suggestContainmentEdges(String source) {
        return null;
    }
}
