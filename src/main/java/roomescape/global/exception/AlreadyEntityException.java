package roomescape.global.exception;

import org.springframework.http.HttpStatus;

public class AlreadyEntityException extends BaseCustomException {

    public AlreadyEntityException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
