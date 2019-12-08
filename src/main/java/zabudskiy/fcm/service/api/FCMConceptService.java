package zabudskiy.fcm.service.api;

public interface FCMConceptService {

    void addHardConcept(String conceptName, String conceptDescription, double outputValue, String mapName);

    void addFlexConcept(String conceptName, String conceptDescription, String mapName, double weight);

    void deleteConceptFromMap(String conceptName, String mapName);
}
