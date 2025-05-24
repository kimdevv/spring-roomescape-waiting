package roomescape.reservation.dao.reservationTime;

import org.springframework.stereotype.Repository;
import roomescape.reservation.model.ReservationTime;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationTimeDaoImpl implements ReservationTimeDao {

    private final JpaReservationTimeDao jpaReservationTimeDao;

    public ReservationTimeDaoImpl(JpaReservationTimeDao jpaReservationTimeDao) {
        this.jpaReservationTimeDao = jpaReservationTimeDao;
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        return jpaReservationTimeDao.save(reservationTime);
    }

    @Override
    public List<ReservationTime> findAll() {
        return jpaReservationTimeDao.findAll();
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        return jpaReservationTimeDao.findById(id);
    }

    @Override
    public int deleteById(Long id) {
        if (jpaReservationTimeDao.existsById(id)) {
            jpaReservationTimeDao.deleteById(id);
            return 1;
        }
        return 0;
    }

    @Override
    public boolean existsByStartAt(LocalTime startAt) {
        return jpaReservationTimeDao.existsByStartAt(startAt);
    }
}
