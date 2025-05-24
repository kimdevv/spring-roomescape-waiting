package roomescape.auth.exception;

import org.springframework.http.HttpStatus;
import roomescape.global.exception.BaseCustomException;

public class InvalidCredentialsException extends BaseCustomException {

    public InvalidCredentialsException() {
        super("이메일 혹은 비밀번호가 잘못되었습니다.", HttpStatus.BAD_REQUEST);
    }
}
