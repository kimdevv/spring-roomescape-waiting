package roomescape.waiting.dao;

import org.springframework.stereotype.Repository;
import roomescape.waiting.model.Waiting;
import roomescape.waiting.model.WaitingWithRank;

import java.util.List;
import java.util.Optional;

@Repository
public class WaitingDaoImpl implements WaitingDao {

    private final JpaWaitingDao jpaWaitingDao;

    public WaitingDaoImpl(JpaWaitingDao jpaWaitingDao) {
        this.jpaWaitingDao = jpaWaitingDao;
    }

    @Override
    public Waiting save(Waiting waiting) {
        return jpaWaitingDao.save(waiting);
    }

    @Override
    public List<Waiting> findAll() {
        return jpaWaitingDao.findAll();
    }

    @Override
    public Optional<Waiting> findById(Long id) {
        return jpaWaitingDao.findById(id);
    }

    @Override
    public List<WaitingWithRank> findWaitingsWithRankByMemberId(Long memberId) {
        return jpaWaitingDao.findWaitingsWithRankByMemberId(memberId);
    }

    @Override
    public boolean existsByDateAndTimeAndThemeAndMember(Waiting waiting) {
        return jpaWaitingDao.existsByDateAndTimeAndThemeAndMember(waiting.getDate(), waiting.getTime(), waiting.getTheme(), waiting.getMember());
    }

    @Override
    public int deleteById(Long id) {
        if (jpaWaitingDao.existsById(id)) {
            jpaWaitingDao.deleteById(id);
            return 1;
        }
        return 0;
    }
}
