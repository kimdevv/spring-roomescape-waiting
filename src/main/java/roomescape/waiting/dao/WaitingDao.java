package roomescape.waiting.dao;

import roomescape.waiting.model.Waiting;
import roomescape.waiting.model.WaitingWithRank;

import java.util.List;
import java.util.Optional;

public interface WaitingDao {

    long save(Waiting waiting);

    List<Waiting> findAll();

    Optional<Waiting> findById(Long id);

    List<WaitingWithRank> findWaitingsWithRankByMemberId(Long memberId);

    boolean existsByDateAndTimeAndThemeAndMember(Waiting waiting);

    int deleteById(Long id);
}
