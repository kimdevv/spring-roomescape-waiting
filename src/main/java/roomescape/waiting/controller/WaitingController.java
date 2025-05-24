package roomescape.waiting.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.auth.model.Principal;
import roomescape.global.annotation.Login;
import roomescape.reservation.dto.response.ReservationGetResponse;
import roomescape.reservation.model.Reservation;
import roomescape.waiting.dto.request.WaitingApplyRequest;
import roomescape.waiting.dto.request.WaitingCreateRequest;
import roomescape.waiting.dto.response.WaitingGetResponse;
import roomescape.waiting.model.Waiting;
import roomescape.waiting.service.WaitingService;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/waitings")
public class WaitingController {

    private final WaitingService waitingService;

    public WaitingController(WaitingService waitingService) {
        this.waitingService = waitingService;
    }

    @PostMapping
    public ResponseEntity<WaitingGetResponse> createWaiting(@RequestBody @Valid WaitingCreateRequest waitingCreateRequest,
                                                            @Login Principal principal) {
        Waiting waiting = waitingService.createWaiting(waitingCreateRequest, principal.memberId());
        WaitingGetResponse waitingResponseDto = new WaitingGetResponse(waiting.getId(), waiting.getName(), waiting.getStartAt(), waiting.getDate(), waiting.getThemeName());
        return ResponseEntity.created(URI.create("/reservations/waiting/" + waiting.getId())).body(waitingResponseDto);
    }

    @GetMapping
    public List<WaitingGetResponse> readAllWaitings() {
        List<Waiting> waitings = waitingService.findAllWaitings();
        return waitings.stream()
                .map(waiting -> new WaitingGetResponse(waiting.getId(), waiting.getName(), waiting.getStartAt(), waiting.getDate(), waiting.getThemeName()))
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWaiting(@PathVariable Long id) {
        waitingService.deleteWaitingById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/apply")
    public ResponseEntity<ReservationGetResponse> applyWaiting(@RequestBody WaitingApplyRequest waitingApplyRequest) {
        Reservation createdReservation = waitingService.apply(waitingApplyRequest);
        return ResponseEntity.created(URI.create("/reservations/" + createdReservation.getId())).body(new ReservationGetResponse(
                createdReservation.getId(),
                createdReservation.getMember().getName(),
                createdReservation.getDate(),
                createdReservation.getTime().getStartAt(),
                createdReservation.getTheme().getName()));
    }
}
