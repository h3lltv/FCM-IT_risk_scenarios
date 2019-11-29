package zabudskiy.fcm.service.impl;

import org.megadix.jfcm.conn.WeightedConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zabudskiy.fcm.model.repository.api.FCMRepository;
import zabudskiy.fcm.service.api.ConnectionService;
import zabudskiy.fcm.service.exceptions.CognitiveMapBadRequestException;
import zabudskiy.fcm.service.exceptions.CognitiveMapNotFoundException;

@Service
public class ConnectionServiceImpl implements ConnectionService {

    private static Logger log = LoggerFactory.getLogger(ConnectionServiceImpl.class);

    private final FCMRepository cognitiveMapRepository;

    @Autowired
    public ConnectionServiceImpl(FCMRepository cognitiveMapRepository) {
        this.cognitiveMapRepository = cognitiveMapRepository;
    }

    @Override
    public void addConnection(String connectionName, String connectionDescription, double weight, String mapName, String fromConceptName, String toConceptName) {
        WeightedConnection connection = new WeightedConnection(connectionName, connectionDescription, weight);
        if (cognitiveMapRepository.mapNotExistWithName(mapName)) {
            log.error("Impossible to add connection {}, cognitive map with name {} not found", connectionName, mapName);
            throw CognitiveMapNotFoundException.mapNotExist(mapName);
        } else if (cognitiveMapRepository.connectionExistInMap(connectionName, mapName)) {
            log.error("Impossible to add this connection, connection with name {} already exist", connectionName);
            throw CognitiveMapBadRequestException.connectionAlreadyExist(connectionName);
        } else if (cognitiveMapRepository.conceptNotExistInMap(fromConceptName, mapName)) {
            log.error("Impossible to add connection {}, concept with name {} not found", connectionName, fromConceptName);
            throw CognitiveMapNotFoundException.conceptNotExist(fromConceptName);
        } else if (cognitiveMapRepository.conceptNotExistInMap(toConceptName, mapName)) {
            log.error("Impossible to add connection {}, concept with name {} not found", connectionName, toConceptName);
            throw CognitiveMapNotFoundException.conceptNotExist(toConceptName);
        } else {
            cognitiveMapRepository.getByName(mapName).ifPresent(map -> {
                map.addConnection(connection);
                map.connect(fromConceptName, connection.getName(), toConceptName);
            });
            log.info("Added connection {} to map {}, from concept {} to concept {} with weight {}",
                    connectionName, mapName, fromConceptName, toConceptName, connection.getWeight());
        }
    }

    @Override
    public void deleteConnectionFromMap(String connectionName, String mapName) {
        if (cognitiveMapRepository.mapNotExistWithName(mapName)) {
            log.error("Impossible to delete connection {}, cognitive map with name {} not found", connectionName, mapName);
            throw CognitiveMapNotFoundException.mapNotExist(mapName);
        } else if (cognitiveMapRepository.connectionNotExistInMap(connectionName, mapName)) {
            log.error("Impossible to delete such connection, connection with name {} not exist", connectionName);
            throw CognitiveMapNotFoundException.connectionNotExist(connectionName);
        } else {
            cognitiveMapRepository.deleteConnection(mapName, connectionName);
            log.info("Connection with name {} in map {} was deleted", connectionName, mapName);
        }
    }
}
