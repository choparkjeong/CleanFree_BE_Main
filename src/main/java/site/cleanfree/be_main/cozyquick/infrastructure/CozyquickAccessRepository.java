package site.cleanfree.be_main.cozyquick.infrastructure;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import site.cleanfree.be_main.cozyquick.domain.CozyquickAccess;

public interface CozyquickAccessRepository extends MongoRepository<CozyquickAccess, String> {

    Optional<CozyquickAccess> findCozyquickAccessByIp(String ip);
}
