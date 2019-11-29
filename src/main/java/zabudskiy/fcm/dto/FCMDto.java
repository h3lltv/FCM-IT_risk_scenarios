package zabudskiy.fcm.dto;

import org.megadix.jfcm.CognitiveMap;
import org.megadix.jfcm.Concept;
import org.megadix.jfcm.FcmConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FCMDto {

    private String name;
    private List<ConceptDto> concepts;
    private List<ConnectionDto> connections;

    public String getName() {
        return name;
    }

    public List<ConceptDto> getConcepts() {
        return concepts;
    }

    public List<ConnectionDto> getConnections() {
        return connections;
    }

    private FCMDto(String name, List<ConceptDto> concepts, List<ConnectionDto> connections) {
        this.name = name;
        this.concepts = concepts;
        this.connections = connections;
    }

    public static FCMDto fromCognitiveMap(CognitiveMap cognitiveMap) {
        return new FCMDto(cognitiveMap.getName(),
                transformConceptsToConceptsDto(new ArrayList<>(cognitiveMap.getConcepts().values())),
                transformConnectionsToConnectionDtos(new ArrayList<>(cognitiveMap.getConnections().values())));
    }

    public static List<ConceptDto> transformConceptsToConceptsDto(List<Concept> concepts) {
        return concepts.stream().map(ConceptDto::fromConcept).collect(Collectors.toList());
    }

    private static List<ConnectionDto> transformConnectionsToConnectionDtos(List<FcmConnection> connections) {
        return connections.stream().map(ConnectionDto::fromConnection).collect(Collectors.toList());
    }
}
