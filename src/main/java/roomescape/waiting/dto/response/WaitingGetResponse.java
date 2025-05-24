package roomescape.waiting.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record WaitingGetResponse(Long id, String name, LocalTime time, LocalDate startAt, String themeName) {

}
