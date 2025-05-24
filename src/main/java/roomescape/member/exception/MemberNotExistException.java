package roomescape.member.exception;

import org.springframework.http.HttpStatus;
import roomescape.global.exception.BaseCustomException;

public class MemberNotExistException extends BaseCustomException {

    public MemberNotExistException() {
        super("멤버를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST);
    }
}
