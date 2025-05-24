package roomescape.reservation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.reservation.model.Reservation;
import roomescape.waiting.model.WaitingWithRank;

import java.time.LocalDate;
import java.time.LocalTime;

public record MyReservationGetResponse(Long id,
                                       String memberName,
                                       @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                       LocalTime startAt,
                                       String themeName,
                                       String status) {

    public static final String RESERVED_STATUS_MESSAGE = "Reserved";
    public static final String WAITING_STATUS_MESSAGE = "%dth waiting";

    public static MyReservationGetResponse from(Reservation reservation) {
        return new MyReservationGetResponse(reservation.getId(), reservation.getMember().getName(), reservation.getDate(), reservation.getTime().getStartAt(), reservation.getTheme().getName(), RESERVED_STATUS_MESSAGE);
    }

    public static MyReservationGetResponse from(WaitingWithRank waitingWithRank) {
        return new MyReservationGetResponse(waitingWithRank.getWaitingId(), waitingWithRank.getName(), waitingWithRank.getDate(), waitingWithRank.getStartAt(), waitingWithRank.getThemeName(), String.format(WAITING_STATUS_MESSAGE, waitingWithRank.getRank()));
    }
}
