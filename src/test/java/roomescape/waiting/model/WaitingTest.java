package roomescape.waiting.model;

import org.junit.jupiter.api.Test;
import roomescape.member.model.Member;
import roomescape.member.model.Role;
import roomescape.reservation.model.ReservationTime;
import roomescape.reservation.model.Theme;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class WaitingTest {

    @Test
    void 날짜와_시간은_null이_될_수_없다() {
        // Given
        LocalDate date = LocalDate.now().plusDays(1);
        ReservationTime time = new ReservationTime(null, LocalTime.now().plusMinutes(1));
        Theme theme = new Theme(null, "theme", "description", "thumbnail");
        Member member = new Member(null, "username", "password", "name", Role.NORMAL);

        // When & Then
        assertAll(() -> {
            assertThatThrownBy(() -> new Waiting(null, null, time, theme, member))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> new Waiting(null, date, null, theme, member))
                    .isInstanceOf(IllegalArgumentException.class);
        });
    }

    @Test
    void 테마는_null이_될_수_없다() {
        // Given
        LocalDate date = LocalDate.now().plusDays(1);
        ReservationTime time = new ReservationTime(null, LocalTime.now().plusMinutes(1));
        Member member = new Member(null, "username", "password", "name", Role.NORMAL);

        // When & Then
        assertThatThrownBy(() -> new Waiting(null, date, time, null, member))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 멤버는_null이_될_수_없다() {
        // Given
        LocalDate date = LocalDate.now().plusDays(1);
        ReservationTime time = new ReservationTime(null, LocalTime.now().plusMinutes(1));
        Theme theme = new Theme(null, "theme", "description", "thumbnail");

        // When & Then
        assertThatThrownBy(() -> new Waiting(null, date, time, theme, null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
