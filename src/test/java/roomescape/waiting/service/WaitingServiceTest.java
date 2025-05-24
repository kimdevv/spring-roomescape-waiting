package roomescape.waiting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.member.dto.request.MemberCreateRequest;
import roomescape.member.model.Member;
import roomescape.member.service.MemberService;
import roomescape.reservation.dto.request.ReservationTimeCreateRequest;
import roomescape.reservation.dto.request.ThemeCreateRequest;
import roomescape.reservation.exception.NotCorrectDateTimeException;
import roomescape.reservation.model.Reservation;
import roomescape.reservation.model.ReservationTime;
import roomescape.reservation.model.Theme;
import roomescape.reservation.service.ReservationService;
import roomescape.reservation.service.ReservationTimeService;
import roomescape.reservation.service.ThemeService;
import roomescape.waiting.dto.request.WaitingApplyRequest;
import roomescape.waiting.dto.request.WaitingCreateRequest;
import roomescape.waiting.exception.DuplicateWaitingException;
import roomescape.waiting.exception.WaitingNotExistException;
import roomescape.waiting.model.Waiting;
import roomescape.waiting.model.WaitingWithRank;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Import(MemberService.class)
public class WaitingServiceTest {

    @Autowired
    private WaitingService waitingService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationTimeService timeService;
    @Autowired
    private ThemeService themeService;

    @Test
    void 예약대기를_추가할_수_있다() {
        // Given
        LocalDate date = LocalDate.now().plusDays(1);
        ReservationTime reservationTime = timeService.createReservationTime(new ReservationTimeCreateRequest(LocalTime.now().plusMinutes(1)));
        Theme theme = themeService.createTheme(new ThemeCreateRequest("theme", "description", "thumbnail"));
        Member member = memberService.createUser(new MemberCreateRequest("email", "password", "name"));

        // When & Then
        assertThatCode(() -> waitingService.createWaiting(new WaitingCreateRequest(date, reservationTime.getId(), theme.getId()), member.getId()))
                .doesNotThrowAnyException();
    }

    @Test
    void 동일한_날짜와_시간_테마에는_중복으로_예약대기를_추가할_수_없다() {
        // Given
        LocalDate date = LocalDate.now().plusDays(1);
        ReservationTime reservationTime = timeService.createReservationTime(new ReservationTimeCreateRequest(LocalTime.now().plusMinutes(1)));
        Theme theme = themeService.createTheme(new ThemeCreateRequest("theme", "description", "thumbnail"));
        Member member = memberService.createUser(new MemberCreateRequest("email", "password", "name"));
        waitingService.createWaiting(new WaitingCreateRequest(date, reservationTime.getId(), theme.getId()), member.getId());

        // When & Then
        assertThatThrownBy(() -> waitingService.createWaiting(new WaitingCreateRequest(date, reservationTime.getId(), theme.getId()), member.getId()))
                .isInstanceOf(DuplicateWaitingException.class);
    }

    @Test
    void 과거_시간에는_예약대기를_생성할_수_없다() {
        // Given
        LocalDate previousDate = LocalDate.now().minusDays(1);
        ReservationTime reservationTime = timeService.createReservationTime(new ReservationTimeCreateRequest(LocalTime.now().plusMinutes(1)));
        Theme theme = themeService.createTheme(new ThemeCreateRequest("theme", "description", "thumbnail"));
        Member member = memberService.createUser(new MemberCreateRequest("email", "password", "name"));

        // When & Then
        assertThatThrownBy(() -> waitingService.createWaiting(new WaitingCreateRequest(previousDate, reservationTime.getId(), theme.getId()), member.getId()))
                .isInstanceOf(NotCorrectDateTimeException.class);
    }

    @Test
    void 모든_예약대기를_조회할_수_있다() {
        // Given
        LocalDate date = LocalDate.now().plusDays(1);
        ReservationTime reservationTime = timeService.createReservationTime(new ReservationTimeCreateRequest(LocalTime.now().plusMinutes(1)));
        Theme theme = themeService.createTheme(new ThemeCreateRequest("theme", "description", "thumbnail"));
        Member member1 = memberService.createUser(new MemberCreateRequest("email1", "password", "name1"));
        Member member2 = memberService.createUser(new MemberCreateRequest("email2", "password", "name2"));
        Member member3 = memberService.createUser(new MemberCreateRequest("email3", "password", "name3"));
        Member member4 = memberService.createUser(new MemberCreateRequest("email4", "password", "name4"));
        waitingService.createWaiting(new WaitingCreateRequest(date, reservationTime.getId(), theme.getId()), member1.getId());
        waitingService.createWaiting(new WaitingCreateRequest(date, reservationTime.getId(), theme.getId()), member2.getId());
        waitingService.createWaiting(new WaitingCreateRequest(date, reservationTime.getId(), theme.getId()), member3.getId());
        waitingService.createWaiting(new WaitingCreateRequest(date, reservationTime.getId(), theme.getId()), member4.getId());

        // When & Then
        assertThat(waitingService.findAllWaitings()).containsExactlyInAnyOrder(
                new Waiting(1L, date, reservationTime, theme, member1),
                new Waiting(2L, date, reservationTime, theme, member2),
                new Waiting(3L, date, reservationTime, theme, member3),
                new Waiting(4L, date, reservationTime, theme, member4)
        );
    }

    @Test
    void 예약대기_id에_해당하는_예약대기_엔티티를_조회할_수_있다() {
        // Given
        LocalDate date = LocalDate.now().plusDays(1);
        ReservationTime reservationTime = timeService.createReservationTime(new ReservationTimeCreateRequest(LocalTime.now().plusMinutes(1)));
        Theme theme = themeService.createTheme(new ThemeCreateRequest("theme", "description", "thumbnail"));
        Member member = memberService.createUser(new MemberCreateRequest("email", "password", "name"));
        Waiting createdWaiting = waitingService.createWaiting(new WaitingCreateRequest(date, reservationTime.getId(), theme.getId()), member.getId());

        // When & Then
        assertThat(waitingService.findWaitingById(createdWaiting.getId())).isEqualTo(new Waiting(createdWaiting.getId(), date, reservationTime, theme, member));
    }

    @Test
    void 예약대기_id에_해당하는_예약대기_엔티티가_없다면_예외를_발생시켜야_한다() {
        // Given
        LocalDate date = LocalDate.now().plusDays(1);
        ReservationTime reservationTime = timeService.createReservationTime(new ReservationTimeCreateRequest(LocalTime.now().plusMinutes(1)));
        Theme theme = themeService.createTheme(new ThemeCreateRequest("theme", "description", "thumbnail"));
        Member member = memberService.createUser(new MemberCreateRequest("email", "password", "name"));
        waitingService.createWaiting(new WaitingCreateRequest(date, reservationTime.getId(), theme.getId()), member.getId());

        // When & Then
        assertThatThrownBy(() -> waitingService.findWaitingById(5000L))
                .isInstanceOf(WaitingNotExistException.class);
    }

    @Test
    void 예약대기_엔티티를_승인하여_예약_엔티티로_등록시킬_수_있다() {
        // Given
        LocalDate date = LocalDate.now().plusDays(1);
        ReservationTime reservationTime = timeService.createReservationTime(new ReservationTimeCreateRequest(LocalTime.now().plusMinutes(1)));
        Theme theme = themeService.createTheme(new ThemeCreateRequest("theme", "description", "thumbnail"));
        Member member = memberService.createUser(new MemberCreateRequest("email", "password", "name"));
        Waiting createdWaiting = waitingService.createWaiting(new WaitingCreateRequest(date, reservationTime.getId(), theme.getId()), member.getId());

        // When
        Reservation createdReservation = waitingService.apply(new WaitingApplyRequest(createdWaiting.getId()));

        // Then
        assertAll(() -> {
            assertThat(createdReservation.getId()).isEqualTo(22L);
            assertThatThrownBy(() -> waitingService.findWaitingById(createdWaiting.getId())).isInstanceOf(WaitingNotExistException.class);
            assertThat(createdReservation).isEqualTo(new Reservation(createdReservation.getId(), member, date, reservationTime, theme));
        });
    }

    @Test
    void 대기_순번과_함께_예약_엔티티를_불러올_수_있다() {
        // Given
        LocalDate date = LocalDate.now().plusDays(1);
        ReservationTime reservationTime = timeService.createReservationTime(new ReservationTimeCreateRequest(LocalTime.now().plusMinutes(1)));
        Theme theme1 = themeService.createTheme(new ThemeCreateRequest("theme1", "description", "thumbnail"));
        Theme theme2 = themeService.createTheme(new ThemeCreateRequest("theme2", "description", "thumbnail"));
        Member member1 = memberService.createUser(new MemberCreateRequest("email1", "password", "name1"));
        Member member2 = memberService.createUser(new MemberCreateRequest("email2", "password", "name2"));
        Member member3 = memberService.createUser(new MemberCreateRequest("email3", "password", "name3"));
        Member finder = memberService.createUser(new MemberCreateRequest("email4", "password", "name4"));
        waitingService.createWaiting(new WaitingCreateRequest(date, reservationTime.getId(), theme1.getId()), member1.getId());
        waitingService.createWaiting(new WaitingCreateRequest(date, reservationTime.getId(), theme1.getId()), member2.getId());
        Waiting saved1 = waitingService.createWaiting(new WaitingCreateRequest(date, reservationTime.getId(), theme1.getId()), finder.getId());
        waitingService.createWaiting(new WaitingCreateRequest(date, reservationTime.getId(), theme1.getId()), member3.getId());
        waitingService.createWaiting(new WaitingCreateRequest(date, reservationTime.getId(), theme2.getId()), member1.getId());
        Waiting saved2 = waitingService.createWaiting(new WaitingCreateRequest(date, reservationTime.getId(), theme2.getId()), finder.getId());
        waitingService.createWaiting(new WaitingCreateRequest(date, reservationTime.getId(), theme2.getId()), member2.getId());
        waitingService.createWaiting(new WaitingCreateRequest(date, reservationTime.getId(), theme2.getId()), member3.getId());

        // When
        List<WaitingWithRank> actual = waitingService.findWaitingsWithRankByMemberId(finder.getId());

        // Then
        assertThat(actual).containsExactlyInAnyOrder(new WaitingWithRank(saved1, 3), new WaitingWithRank(saved2, 2));
    }
}
