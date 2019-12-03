package ptash.petr.cognitivemaps.service.impl;

import org.megadix.jfcm.Concept;
import org.megadix.jfcm.act.SigmoidActivator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ptash.petr.cognitivemaps.service.exceptions.CognitiveMapBadRequestException;
import ptash.petr.cognitivemaps.service.exceptions.CognitiveMapNotFoundException;
import ptash.petr.cognitivemaps.model.repository.api.CognitiveMapRepository;
import ptash.petr.cognitivemaps.service.api.ConceptService;

@Service
public class ConceptServiceImpl implements ConceptService {

    private static final Logger log = LoggerFactory.getLogger(ConceptServiceImpl.class);

    private final CognitiveMapRepository cognitiveMapRepository;

    @Autowired
    public ConceptServiceImpl(CognitiveMapRepository cognitiveMapRepository) {
        this.cognitiveMapRepository = cognitiveMapRepository;
    }

    @Override
    public void addHardConcept(String conceptName, String conceptDescription, double outputValue, String mapName) {
        Concept concept = new Concept(conceptName, conceptDescription, new SigmoidActivator(), 0.0, outputValue, true);
        addConcept(concept, mapName);
    }

    @Override
    public void addFlexConcept(String conceptName, String conceptDescription, double outputValue, String mapName) {
        Concept concept = new Concept(conceptName, conceptDescription, new SigmoidActivator(), 0.0, outputValue, false);
        addConcept(concept, mapName);
    }

    private void addConcept(Concept concept, String mapName) {
        String conceptName = concept.getName();
        if (cognitiveMapRepository.mapNotExistWithName(mapName)) {
            log.error("Impossible to add concept to this cognitive map, map with name {} not exist", mapName);
            throw CognitiveMapNotFoundException.mapNotExist(mapName);
        } else if (cognitiveMapRepository.conceptExistInMap(conceptName, mapName)) {
            log.error("Impossible to add this concept to cognitive map, concept with name {} already exist", conceptName);
            throw CognitiveMapBadRequestException.conceptAlreadyExist(conceptName);
        } else {
            cognitiveMapRepository.getByName(mapName).ifPresent(map -> map.addConcept(concept));
            log.info("Concept {} was added to cognitive map {}", conceptName, mapName);
        }
    }

    @Override
    public void deleteConceptFromMap(String conceptName, String mapName) {
        if (cognitiveMapRepository.mapNotExistWithName(mapName)) {
            log.error("Cognitive map with name {} not exist", mapName);
            throw CognitiveMapNotFoundException.mapNotExist(mapName);
        } else if (cognitiveMapRepository.conceptNotExistInMap(conceptName, mapName)) {
            log.error("Concept with name {} in cognitive map {} not exist", conceptName, mapName);
            throw CognitiveMapNotFoundException.conceptNotExist(conceptName);
        } else {
            cognitiveMapRepository.deleteConcept(mapName, conceptName);
            log.info("Concept with name {} in map {} was deleted", conceptName, mapName);
        }
    }


}
