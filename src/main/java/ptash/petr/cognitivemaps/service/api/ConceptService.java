package ptash.petr.cognitivemaps.service.api;

public interface ConceptService {

    void addHardConcept(String conceptName, String conceptDescription, double outputValue, String mapName);

    void addFlexConcept(String conceptName, String conceptDescription, double outputValue, String mapName);

    void deleteConceptFromMap(String conceptName, String mapName);
}
