package ptash.petr.cognitivemaps.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CognitiveMapNotFoundException extends RuntimeException {

    private static final String COMMON_MESSAGE_SNIPPET = " not found";

    private CognitiveMapNotFoundException(String message) {
        super(message);
    }

    public static CognitiveMapNotFoundException mapNotExist(String name) {
        return new CognitiveMapNotFoundException("Cognitive map with name " + name + COMMON_MESSAGE_SNIPPET);
    }

    public static CognitiveMapNotFoundException conceptNotExist(String name) {
        return new CognitiveMapNotFoundException("Concept with name " + name + COMMON_MESSAGE_SNIPPET);
    }

    public static CognitiveMapNotFoundException connectionNotExist(String name) {
        return new CognitiveMapNotFoundException("Connection with name " + name + COMMON_MESSAGE_SNIPPET);
    }
}
