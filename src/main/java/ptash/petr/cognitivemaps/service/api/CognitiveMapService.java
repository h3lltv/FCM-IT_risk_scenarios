package ptash.petr.cognitivemaps.service.api;

import ptash.petr.cognitivemaps.web.protocol.response.CognitiveMapDto;

import java.util.List;

public interface CognitiveMapService {

    void createNewMap(String name);

    CognitiveMapDto getByName(String name);

    List<CognitiveMapDto> getAll();

    CognitiveMapDto execute(String name);

    CognitiveMapDto reset(String name);

    void deleteCognitiveMap(String mapName);

}
