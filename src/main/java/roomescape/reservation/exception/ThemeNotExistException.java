package roomescape.reservation.exception;

import org.springframework.http.HttpStatus;
import roomescape.global.exception.BaseCustomException;

public class ThemeNotExistException extends BaseCustomException {

    public ThemeNotExistException() {
        super("테마를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST);
    }
}
