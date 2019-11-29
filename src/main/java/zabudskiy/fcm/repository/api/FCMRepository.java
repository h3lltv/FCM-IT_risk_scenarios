package zabudskiy.fcm.repository.api;

import org.megadix.jfcm.CognitiveMap;

import java.util.List;
import java.util.Optional;

public interface FCMRepository {

    void save(CognitiveMap cognitiveMap);

    List<CognitiveMap> getAll();

    Optional<CognitiveMap> getByName(String name);

    void deleteCognitiveMap(String mapName);

    void deleteConcept(String mapName, String conceptName);

    void deleteConnection(String mapName, String connectionName);

    boolean mapExistWithName(String name);

    boolean mapNotExistWithName(String name);

    boolean conceptExistInMap(String conceptName, String mapName);

    boolean conceptNotExistInMap(String conceptName, String mapName);

    boolean connectionExistInMap(String connectionName, String mapName);

    boolean connectionNotExistInMap(String connectionName, String mapName);
}
