package roomescape.waiting.exception;

import org.springframework.http.HttpStatus;
import roomescape.global.exception.BaseCustomException;

public class DuplicateWaitingException extends BaseCustomException {

    public DuplicateWaitingException(String message) {
        super(message , HttpStatus.CONFLICT);
    }
}
