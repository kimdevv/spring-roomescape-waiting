package roomescape.waiting.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.global.exception.InvalidInputException;
import roomescape.member.model.Member;
import roomescape.member.service.MemberService;
import roomescape.reservation.dto.request.ReservationCreateRequest;
import roomescape.reservation.exception.NotCorrectDateTimeException;
import roomescape.reservation.model.Reservation;
import roomescape.reservation.model.ReservationTime;
import roomescape.reservation.model.Theme;
import roomescape.reservation.service.ReservationService;
import roomescape.reservation.service.ReservationTimeService;
import roomescape.reservation.service.ThemeService;
import roomescape.waiting.dao.WaitingDao;
import roomescape.waiting.dto.request.WaitingApplyRequest;
import roomescape.waiting.dto.request.WaitingCreateRequest;
import roomescape.waiting.exception.DuplicateWaitingException;
import roomescape.waiting.exception.WaitingNotExistException;
import roomescape.waiting.model.Waiting;
import roomescape.waiting.model.WaitingWithRank;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class WaitingService {

    private final WaitingDao waitingDao;
    private final MemberService memberService;
    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;
    private final ThemeService themeService;

    public WaitingService(WaitingDao waitingDao, MemberService memberService, ReservationService reservationService, ReservationTimeService reservationTimeService, ThemeService themeService) {
        this.waitingDao = waitingDao;
        this.memberService = memberService;
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
        this.themeService = themeService;
    }

    public Waiting createWaiting(WaitingCreateRequest waitingCreateRequest, Long memberId) {
        Member member = memberService.findMemberById(memberId);
        ReservationTime reservationTime = reservationTimeService.findReservationTimeById(waitingCreateRequest.timeId());
        Theme theme = themeService.findThemeById(waitingCreateRequest.themeId());
        Waiting waiting = new Waiting(null, waitingCreateRequest.date(), reservationTime, theme, member);
        validateDuplicateWaiting(waiting);
        validateAddReservationDateTime(waiting);
        return waitingDao.save(waiting);
    }

    private void validateDuplicateWaiting(Waiting waiting) {
        if (waitingDao.existsByDateAndTimeAndThemeAndMember(waiting)) {
            throw new DuplicateWaitingException("중복된 예약대기 신청입니다");
        }
    }

    private void validateAddReservationDateTime(Waiting waiting) {
        LocalDateTime currentDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        if (waiting.isBeforeDateTime(currentDateTime)) {
            throw new NotCorrectDateTimeException("과거 시간에 예약할 수 없습니다.");
        }
    }

    public List<Waiting> findAllWaitings() {
        return waitingDao.findAll();
    }

    public Waiting findWaitingById(Long id) {
        return waitingDao.findById(id)
                .orElseThrow(WaitingNotExistException::new);
    }

    public List<WaitingWithRank> findWaitingsWithRankByMemberId(Long memberId) {
        return waitingDao.findWaitingsWithRankByMemberId(memberId);
    }

    public void deleteWaitingById(Long id) {
        if (waitingDao.deleteById(id) == 0) {
            throw new InvalidInputException("존재하지 않는 예약대기 id입니다.");
        }
    }

    @Transactional
    public Reservation apply(WaitingApplyRequest waitingApplyRequest) {
        Long waitingId = waitingApplyRequest.id();
        Waiting waiting = findWaitingById(waitingId);
        Reservation createdReservation = reservationService.createReservation(new ReservationCreateRequest(waiting.getDate(), waiting.getTime().getId(), waiting.getTheme().getId()), waiting.getMember());
        deleteWaitingById(waitingId);
        return createdReservation;
    }
}
