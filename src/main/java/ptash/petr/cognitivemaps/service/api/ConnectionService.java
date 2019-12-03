package ptash.petr.cognitivemaps.service.api;

public interface ConnectionService {

    void addConnection(String connectionName, String connectionDescription, double weight, String mapName, String fromConceptName, String toConceptName);

    void deleteConnectionFromMap(String connectionName, String mapName);

}
