package site.cleanfree.be_main.cozyhouse;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CozyHouseAccessRepository extends MongoRepository<CozyHouseAccess, String> {

    Optional<CozyHouseAccess> findCozyHouseAccessByIp(String ip);
}
