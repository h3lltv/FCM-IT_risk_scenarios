package zabudskiy.fcm.service.api;



import zabudskiy.fcm.web.protocol.response.FCMDto;

import java.util.List;

public interface FCMService {

    void createNewMap(String name);

    FCMDto getByName(String name);

    List<FCMDto> getAll();

    FCMDto execute(String name);

    FCMDto reset(String name);

    void deleteCognitiveMap(String mapName);

}
