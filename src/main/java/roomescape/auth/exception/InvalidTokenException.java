package roomescape.auth.exception;

import org.springframework.http.HttpStatus;
import roomescape.global.exception.BaseCustomException;

public class InvalidTokenException extends BaseCustomException {

    public InvalidTokenException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
