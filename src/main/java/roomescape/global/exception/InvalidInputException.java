package roomescape.global.exception;

import org.springframework.http.HttpStatus;

public class InvalidInputException extends BaseCustomException {

    public InvalidInputException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
