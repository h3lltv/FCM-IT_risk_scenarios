package ptash.petr.cognitivemaps.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CognitiveMapBadRequestException extends RuntimeException {

    private static final String COMMON_MESSAGE_SNIPPET = " already exist";

    private CognitiveMapBadRequestException(String message) {
        super(message);
    }

    public static CognitiveMapBadRequestException mapAlreadyExist(String name) {
        return new CognitiveMapBadRequestException("Cognitive map with name " + name + COMMON_MESSAGE_SNIPPET);
    }

    public static CognitiveMapBadRequestException conceptAlreadyExist(String name) {
        return new CognitiveMapBadRequestException("Concept with name " + name + COMMON_MESSAGE_SNIPPET);
    }

    public static CognitiveMapBadRequestException cantExecuteMap(String name) {
        return new CognitiveMapBadRequestException("Impossible to execute map with name " + name);
    }

    public static CognitiveMapBadRequestException connectionAlreadyExist(String name) {
        return new CognitiveMapBadRequestException("Connection with name " + name + COMMON_MESSAGE_SNIPPET);
    }
}
