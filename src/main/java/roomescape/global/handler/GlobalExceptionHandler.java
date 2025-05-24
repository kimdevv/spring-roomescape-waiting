package roomescape.global.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.global.dto.response.ErrorResponse;
import roomescape.global.exception.AlreadyEntityException;
import roomescape.global.exception.BaseCustomException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AlreadyEntityException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyEntity(AlreadyEntityException exception) {
        exception.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(generateErrorResponse("서버 오류가 발생하였습니다. 관리자에게 문의하세요."));
    }

    @ExceptionHandler(value = BaseCustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomExceptions(BaseCustomException exception) {
        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(generateErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception exception) {
        exception.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(generateErrorResponse("예상하지 못한 예외가 발생했습니다. 자세한 사항은 관리자에게 문의하세요."));
    }

    private ErrorResponse generateErrorResponse(String message) {
        return new ErrorResponse(message);
    }
}
