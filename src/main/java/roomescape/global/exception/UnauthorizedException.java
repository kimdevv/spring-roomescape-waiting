package roomescape.global.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseCustomException {

    public UnauthorizedException() {
        super("로그인 해주세요.", HttpStatus.UNAUTHORIZED);
    }
}
