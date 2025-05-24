package roomescape.reservation.exception;

import org.springframework.http.HttpStatus;
import roomescape.global.exception.BaseCustomException;

public class DuplicateReservationException extends BaseCustomException {

    public DuplicateReservationException() {
        super("해당 시간에는 이미 예약이 존재합니다.", HttpStatus.CONFLICT);
    }
}
