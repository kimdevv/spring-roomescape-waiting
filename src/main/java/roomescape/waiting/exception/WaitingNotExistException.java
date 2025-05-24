package roomescape.waiting.exception;

public class WaitingNotExistException extends RuntimeException {

    public WaitingNotExistException() {
        super("예약대기를 찾을 수 없습니다.");
    }
}
