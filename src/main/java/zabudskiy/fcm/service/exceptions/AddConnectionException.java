package zabudskiy.fcm.service.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AddConnectionException extends RuntimeException {

    public AddConnectionException(String message) {
        super(message);
    }
}
