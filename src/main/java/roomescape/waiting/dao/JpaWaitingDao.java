package roomescape.waiting.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import roomescape.member.model.Member;
import roomescape.reservation.model.ReservationTime;
import roomescape.reservation.model.Theme;
import roomescape.waiting.model.Waiting;
import roomescape.waiting.model.WaitingWithRank;

import java.time.LocalDate;
import java.util.List;

public interface JpaWaitingDao extends JpaRepository<Waiting, Long> {

    @Query("SELECT new roomescape.waiting.model.WaitingWithRank(" +
            "    w, " +
            "    (SELECT COUNT(w2) + 1L " +
            "     FROM Waiting w2 " +
            "     WHERE w2.theme = w.theme " +
            "       AND w2.date = w.date " +
            "       AND w2.time = w.time " +
            "       AND w2.id < w.id)) " +
            "FROM Waiting w " +
            "WHERE w.member.id = :memberId")
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    List<WaitingWithRank> findWaitingsWithRankByMemberId(Long memberId);

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    boolean existsByDateAndTimeAndThemeAndMember(LocalDate date, ReservationTime time, Theme theme, Member member);
}
