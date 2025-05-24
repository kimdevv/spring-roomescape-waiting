package roomescape.waiting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import roomescape.waiting.dao.WaitingDao;
import roomescape.waiting.dao.WaitingDaoImpl;

@Configuration
public class WaitingConfig {

    @Bean
    public WaitingDao waitingDao(WaitingDaoImpl waitingDao) {
        return waitingDao;
    }
}
