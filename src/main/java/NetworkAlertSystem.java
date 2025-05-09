import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkAlertSystem implements AlertNetwork {
    private final Map<String, List<String>> serviceDependencies = new HashMap<>();

    public Map<String, List<String>> getServiceDependencies() {
        return serviceDependencies;
    }

    @Override
    public void addService(String service) {
    }

    @Override
    public void addDependency(String fromService, String toService) {

    }

    @Override
    public List<String> getDependencies(String service) {
        return null;
    }

    @Override
    public List<String> findAlertPropagationPath(String source, String target) {
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
