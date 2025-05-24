package roomescape.global.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends BaseCustomException {

    public ForbiddenException() {
        super("접근 권한이 부족합니다.", HttpStatus.FORBIDDEN);
    }
}
