package ptash.petr.cognitivemaps.model.repository.impl;

import org.megadix.jfcm.CognitiveMap;
import org.springframework.stereotype.Component;
import ptash.petr.cognitivemaps.model.repository.api.CognitiveMapRepository;

import java.util.*;

@Component
public class CognitiveMapRepositoryImpl implements CognitiveMapRepository {

    private Map<String, CognitiveMap> cognitiveMaps = new HashMap<>();

    public void save(CognitiveMap cognitiveMap) {
        this.cognitiveMaps.put(cognitiveMap.getName(), cognitiveMap);
    }

    @Override
    public List<CognitiveMap> getAll() {
        if (cognitiveMaps.isEmpty()) return Collections.emptyList();
        return new ArrayList<>(this.cognitiveMaps.values());
    }

    @Override
    public Optional<CognitiveMap> getByName(String name) {
        if (mapExistWithName(name)) {
            return Optional.of(this.cognitiveMaps.get(name));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void deleteCognitiveMap(String mapName) {
        cognitiveMaps.remove(mapName);
    }

    @Override
    public void deleteConcept(String mapName, String conceptName) {
        cognitiveMaps.get(mapName).getConcepts().remove(conceptName);
    }

    @Override
    public void deleteConnection(String mapName, String connectionName) {
        cognitiveMaps.get(mapName).getConnections().remove(connectionName);
    }

    @Override
    public boolean mapExistWithName(String name) {
        return this.cognitiveMaps.containsKey(name);
    }

    @Override
    public boolean mapNotExistWithName(String name) {
        return !mapExistWithName(name);
    }

    @Override
    public boolean conceptExistInMap(String conceptName, String mapName) {
        return cognitiveMaps.get(mapName).getConcepts().containsKey(conceptName);
    }

    @Override
    public boolean conceptNotExistInMap(String conceptName, String mapName) {
        return !conceptExistInMap(conceptName, mapName);
    }

    @Override
    public boolean connectionExistInMap(String connectionName, String mapName) {
        return cognitiveMaps.get(mapName).getConnections().containsKey(connectionName);
    }

    @Override
    public boolean connectionNotExistInMap(String connectionName, String mapName) {
        return !connectionExistInMap(connectionName, mapName);
    }
}
