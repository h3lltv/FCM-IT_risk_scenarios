package zabudskiy.fcm.service.impl;

import org.megadix.jfcm.CognitiveMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zabudskiy.fcm.repository.api.FCMRepository;
import zabudskiy.fcm.service.api.FCMService;
import zabudskiy.fcm.service.exceptions.CognitiveMapBadRequestException;
import zabudskiy.fcm.service.exceptions.CognitiveMapNotFoundException;
import zabudskiy.fcm.dto.FCMDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FCMServiceImpl implements FCMService {

    private static Logger log = LoggerFactory.getLogger(FCMServiceImpl.class);

    private final FCMRepository cognitiveMapRepository;

    @Autowired
    public FCMServiceImpl(FCMRepository cognitiveMapRepository) {
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
    public FCMDto getByName(String name) {
        Optional<CognitiveMap> cognitiveMapOptional = cognitiveMapRepository.getByName(name);
        if (cognitiveMapOptional.isPresent()) {
            CognitiveMap cognitiveMap = cognitiveMapOptional.get();
            return FCMDto.fromCognitiveMap(cognitiveMap);
        } else {
            log.error("Cognitive map with name {} not found", name);
            throw CognitiveMapNotFoundException.mapNotExist(name);
        }
    }

    @Override
    public List<FCMDto> getAll() {
        return cognitiveMapRepository.getAll().stream().map(FCMDto::fromCognitiveMap).collect(Collectors.toList());
    }

    @Override
    public FCMDto execute(String name) {
        Optional<CognitiveMap> cognitiveMapOptional = cognitiveMapRepository.getByName(name);
        CognitiveMap cognitiveMap = cognitiveMapOptional.orElseThrow(() -> CognitiveMapNotFoundException.mapNotExist(name));
        try {
            cognitiveMap.execute();
            log.info("Cognitive map with name {} executed", name);
            return FCMDto.fromCognitiveMap(cognitiveMap);
        } catch (Exception e) {
            log.error("Impossible to execute map with name {}, you may have a flexible concepts without any connection", name);
            throw CognitiveMapBadRequestException.cantExecuteMap(name);
        }
    }

    @Override
    public FCMDto reset(String name) {
        Optional<CognitiveMap> cognitiveMapOptional = cognitiveMapRepository.getByName(name);
        if (cognitiveMapOptional.isPresent()) {
            CognitiveMap cognitiveMap = cognitiveMapOptional.get();
            cognitiveMap.reset();
            log.info("Cognitive map with name {} was reseted", name);
            return FCMDto.fromCognitiveMap(cognitiveMap);
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
