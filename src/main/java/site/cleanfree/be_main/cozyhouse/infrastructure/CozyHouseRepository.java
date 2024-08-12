package site.cleanfree.be_main.cozyhouse.infrastructure;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import site.cleanfree.be_main.cozyhouse.domain.CozyHouse;

public interface CozyHouseRepository extends MongoRepository<CozyHouse, String> {

    Optional<CozyHouse> findCozyHouseByIp(String ip);
}
