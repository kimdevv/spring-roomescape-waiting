package roomescape.reservation.exception;

import org.springframework.http.HttpStatus;
import roomescape.global.exception.BaseCustomException;

public class DuplicateTimeException extends BaseCustomException {

    public DuplicateTimeException() {
        super("이미 존재하는 시간입니다.", HttpStatus.CONFLICT);
    }
}
