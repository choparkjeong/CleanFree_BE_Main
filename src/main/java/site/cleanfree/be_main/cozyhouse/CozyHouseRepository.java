package site.cleanfree.be_main.cozyhouse;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CozyHouseRepository extends MongoRepository<CozyHouse, String> {

    Optional<CozyHouse> findCozyHouseByIp(String ip);
}
