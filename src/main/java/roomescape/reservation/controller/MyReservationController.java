package roomescape.reservation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.auth.model.Principal;
import roomescape.global.annotation.Login;
import roomescape.reservation.dto.response.MyReservationGetResponse;
import roomescape.reservation.model.Reservation;
import roomescape.reservation.service.ReservationService;
import roomescape.waiting.model.WaitingWithRank;
import roomescape.waiting.service.WaitingService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reservations/mine")
public class MyReservationController {

    private final ReservationService reservationService;
    private final WaitingService waitingService;

    public MyReservationController(ReservationService reservationService, WaitingService waitingService) {
        this.reservationService = reservationService;
        this.waitingService = waitingService;
    }

    @GetMapping
    public List<MyReservationGetResponse> readMyReservations(@Login Principal principal) {
        List<MyReservationGetResponse> myReservationGetResponses = new ArrayList<>();
        List<Reservation> myReservations = reservationService.findByMemberId(principal.memberId());
        for (Reservation reservation : myReservations) {
            myReservationGetResponses.add(MyReservationGetResponse.from(reservation));
        }
        List<WaitingWithRank> myWaitings = waitingService.findWaitingsWithRankByMemberId(principal.memberId());
        for (WaitingWithRank waitingWithRank : myWaitings) {
            myReservationGetResponses.add(MyReservationGetResponse.from(waitingWithRank));
        }
        return myReservationGetResponses;
    }
}
