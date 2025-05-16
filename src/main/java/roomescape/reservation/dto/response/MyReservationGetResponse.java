package roomescape.reservation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public record MyReservationGetResponse(Long id,
                                       String memberName,
                                       @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                       LocalTime startAt,
                                       String themeName,
                                       String status) {

}
