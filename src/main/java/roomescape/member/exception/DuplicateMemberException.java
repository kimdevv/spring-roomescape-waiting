package roomescape.member.exception;

import org.springframework.http.HttpStatus;
import roomescape.global.exception.BaseCustomException;

public class DuplicateMemberException extends BaseCustomException {

    public DuplicateMemberException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
