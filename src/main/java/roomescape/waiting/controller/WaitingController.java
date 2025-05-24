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
@RequestMapping("/reservations/waiting")
public class WaitingController {

    private final WaitingService waitingService;

    public WaitingController(WaitingService waitingService) {
        this.waitingService = waitingService;
    }

    @PostMapping
    public ResponseEntity<WaitingGetResponse> addWaiting(@RequestBody @Valid WaitingCreateRequest waitingCreateRequest,
                                                         @Login Principal principal) {
        long addedWaitingId = waitingService.createWaiting(waitingCreateRequest, principal.memberId());
        Waiting waiting = waitingService.findWaitingById(addedWaitingId);

        WaitingGetResponse waitingResponseDto = new WaitingGetResponse(addedWaitingId, waiting.getName(), waiting.getStartAt(), waiting.getDate(), waiting.getThemeName());
        return ResponseEntity.created(URI.create("/reservations/waiting/" + addedWaitingId)).body(waitingResponseDto);
    }

    @GetMapping
    public List<WaitingGetResponse> waitings() {
        List<Waiting> waitings = waitingService.findAllWaitings();
        return waitings.stream()
                .map(waiting -> new WaitingGetResponse(waiting.getId(), waiting.getName(), waiting.getStartAt(), waiting.getDate(), waiting.getThemeName()))
                .toList();
    }

}
