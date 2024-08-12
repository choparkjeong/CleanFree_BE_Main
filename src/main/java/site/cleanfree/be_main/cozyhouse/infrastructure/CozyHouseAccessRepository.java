package site.cleanfree.be_main.cozyhouse.infrastructure;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import site.cleanfree.be_main.cozyhouse.domain.CozyHouseAccess;

public interface CozyHouseAccessRepository extends MongoRepository<CozyHouseAccess, String> {

    Optional<CozyHouseAccess> findCozyHouseAccessByIp(String ip);
}
