package roomescape.reservation.exception;

import org.springframework.http.HttpStatus;
import roomescape.global.exception.BaseCustomException;

public class DuplicateThemeException extends BaseCustomException {

    public DuplicateThemeException() {
        super("해당 이름을 가진 테마가 이미 존재합니다.", HttpStatus.CONFLICT);
    }
}
