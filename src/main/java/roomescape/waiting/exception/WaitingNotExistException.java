package roomescape.waiting.exception;

import org.springframework.http.HttpStatus;
import roomescape.global.exception.BaseCustomException;

public class WaitingNotExistException extends BaseCustomException {

    public WaitingNotExistException() {
        super("예약대기를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST);
    }
}
