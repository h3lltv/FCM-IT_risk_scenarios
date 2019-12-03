package ptash.petr.cognitivemaps.service.impl;

import org.megadix.jfcm.CognitiveMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ptash.petr.cognitivemaps.web.protocol.response.CognitiveMapDto;
import ptash.petr.cognitivemaps.service.exceptions.CognitiveMapBadRequestException;
import ptash.petr.cognitivemaps.model.repository.api.CognitiveMapRepository;
import ptash.petr.cognitivemaps.service.exceptions.CognitiveMapNotFoundException;
import ptash.petr.cognitivemaps.service.api.CognitiveMapService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CognitiveMapServiceImpl implements CognitiveMapService {

    private static Logger log = LoggerFactory.getLogger(CognitiveMapServiceImpl.class);

    private final CognitiveMapRepository cognitiveMapRepository;

    @Autowired
    public CognitiveMapServiceImpl(CognitiveMapRepository cognitiveMapRepository) {
        this.cognitiveMapRepository = cognitiveMapRepository;
    }

    @Override
    public void createNewMap(String name) {
        if (cognitiveMapRepository.mapNotExistWithName(name)) {
            cognitiveMapRepository.save(new CognitiveMap(name));
            log.info("Created new cognitive map with name {}", name);
        } else {
            log.error("Impossible to create cognitive map with name {}", name);
            throw CognitiveMapBadRequestException.mapAlreadyExist(name);
        }
    }

    @Override
    public CognitiveMapDto getByName(String name) {
        Optional<CognitiveMap> cognitiveMapOptional = cognitiveMapRepository.getByName(name);
        if (cognitiveMapOptional.isPresent()) {
            CognitiveMap cognitiveMap = cognitiveMapOptional.get();
            return CognitiveMapDto.fromCognitiveMap(cognitiveMap);
        } else {
            log.error("Cognitive map with name {} not found", name);
            throw CognitiveMapNotFoundException.mapNotExist(name);
        }
    }

    @Override
    public List<CognitiveMapDto> getAll() {
        return cognitiveMapRepository.getAll().stream().map(CognitiveMapDto::fromCognitiveMap).collect(Collectors.toList());
    }

    @Override
    public CognitiveMapDto execute(String name) {
        Optional<CognitiveMap> cognitiveMapOptional = cognitiveMapRepository.getByName(name);
        CognitiveMap cognitiveMap = cognitiveMapOptional.orElseThrow(() -> CognitiveMapNotFoundException.mapNotExist(name));
        try {
            cognitiveMap.execute();
            log.info("Cognitive map with name {} executed", name);
            return CognitiveMapDto.fromCognitiveMap(cognitiveMap);
        } catch (Exception e) {
            log.error("Impossible to execute map with name {}, you may have a flexible concepts without any connection", name);
            throw CognitiveMapBadRequestException.cantExecuteMap(name);
        }
    }

    @Override
    public CognitiveMapDto reset(String name) {
        Optional<CognitiveMap> cognitiveMapOptional = cognitiveMapRepository.getByName(name);
        if (cognitiveMapOptional.isPresent()) {
            CognitiveMap cognitiveMap = cognitiveMapOptional.get();
            cognitiveMap.reset();
            log.info("Cognitive map with name {} was reseted", name);
            return CognitiveMapDto.fromCognitiveMap(cognitiveMap);
        } else {
            log.error("Impossible ti reset cognitive map with name {}, map not found", name);
            throw CognitiveMapNotFoundException.mapNotExist(name);
        }
    }

    @Override
    public void deleteCognitiveMap(String mapName) {
        if (cognitiveMapRepository.mapExistWithName(mapName)) {
            cognitiveMapRepository.deleteCognitiveMap(mapName);
            log.info("Cognitive map with name {} was deleted", mapName);
        } else {
            log.error("Impossible to delete cognitive map with name {}, map not found", mapName);
            throw CognitiveMapNotFoundException.mapNotExist(mapName);
        }
    }

}
