package roomescape.reservation.exception;

import org.springframework.http.HttpStatus;
import roomescape.global.exception.BaseCustomException;

public class TimeNotExistException extends BaseCustomException {

    public TimeNotExistException() {
        super("예약 시간을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST);
    }
}
