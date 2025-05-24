package roomescape.reservation.exception;

import org.springframework.http.HttpStatus;
import roomescape.global.exception.BaseCustomException;

public class NotCorrectDateTimeException extends BaseCustomException {

    public NotCorrectDateTimeException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
