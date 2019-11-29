package zabudskiy.fcm.service.impl;

import org.megadix.jfcm.Concept;
import org.megadix.jfcm.act.SigmoidActivator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zabudskiy.fcm.model.repository.api.FCMRepository;
import zabudskiy.fcm.service.api.FCMConceptService;
import zabudskiy.fcm.service.exceptions.CognitiveMapBadRequestException;
import zabudskiy.fcm.service.exceptions.CognitiveMapNotFoundException;


@Service
public class ConceptServiceImpl implements FCMConceptService {

    private static final Logger log = LoggerFactory.getLogger(ConceptServiceImpl.class);

    private final FCMRepository fcmRepository;

    @Autowired
    public ConceptServiceImpl(FCMRepository fcmRepository) {
        this.fcmRepository = fcmRepository;
    }

    @Override
    public void addHardConcept(String conceptName, String conceptDescription, double outputValue, String mapName) {
        Concept concept = new Concept(conceptName, conceptDescription, new SigmoidActivator(), 0.0, outputValue, true);
        addConcept(concept, mapName);
    }

    @Override
    public void addFlexConcept(String conceptName, String conceptDescription, String mapName) {
        Concept concept = new Concept(conceptName, conceptDescription, new SigmoidActivator(), 0.0, 0.0, false);
        addConcept(concept, mapName);
    }

    private void addConcept(Concept concept, String mapName) {
        String conceptName = concept.getName();
        if (fcmRepository.mapNotExistWithName(mapName)) {
            log.error("Impossible to add concept to this cognitive map, map with name {} not exist", mapName);
            throw CognitiveMapNotFoundException.mapNotExist(mapName);
        } else if (fcmRepository.conceptExistInMap(conceptName, mapName)) {
            log.error("Impossible to add this concept to cognitive map, concept with name {} already exist", conceptName);
            throw CognitiveMapBadRequestException.conceptAlreadyExist(conceptName);
        } else {
            fcmRepository.getByName(mapName).ifPresent(map -> map.addConcept(concept));
            log.info("Concept {} was added to cognitive map {}", conceptName, mapName);
        }
    }

    @Override
    public void deleteConceptFromMap(String conceptName, String mapName) {
        if (fcmRepository.mapNotExistWithName(mapName)) {
            log.error("Cognitive map with name {} not exist", mapName);
            throw CognitiveMapNotFoundException.mapNotExist(mapName);
        } else if (fcmRepository.conceptNotExistInMap(conceptName, mapName)) {
            log.error("Concept with name {} in cognitive map {} not exist", conceptName, mapName);
            throw CognitiveMapNotFoundException.conceptNotExist(conceptName);
        } else {
            fcmRepository.deleteConcept(mapName, conceptName);
            log.info("Concept with name {} in map {} was deleted", conceptName, mapName);
        }
    }
}
