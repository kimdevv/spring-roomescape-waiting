package roomescape.waiting.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record WaitingCreateRequest(
        @JsonFormat(pattern = "yyyy-MM-dd") @NotNull(message = "대기할 날짜가 입력되지 않았습니다.") LocalDate date,
        @NotNull(message = "대기할 시간이 입력되지 않았습니다.") Long timeId,
        @NotNull(message = "대기할 테마가 입력되지 않았습니다.") Long themeId) {

}
