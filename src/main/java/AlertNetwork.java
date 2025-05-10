import java.util.List;
import java.util.Set;

public interface AlertNetwork {
    void addService(String service);
    void addDependency(String fromService, String toService); // Directed edge
    List<String> getDependencies(String service);
    List<String> findAlertPropagationPath(String source, String target);
    Set<String> getAffectedServices(String source);
    List<Pair<String, String>> suggestContainmentEdges(String source);
}
