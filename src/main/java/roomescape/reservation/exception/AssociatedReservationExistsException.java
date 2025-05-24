package roomescape.reservation.exception;

import org.springframework.http.HttpStatus;
import roomescape.global.exception.BaseCustomException;

public class AssociatedReservationExistsException extends BaseCustomException {

    public AssociatedReservationExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
