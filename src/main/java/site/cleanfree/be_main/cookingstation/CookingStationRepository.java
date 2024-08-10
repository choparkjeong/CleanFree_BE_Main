package site.cleanfree.be_main.cookingstation;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CookingStationRepository extends MongoRepository<CookingStation, String> {

    Optional<CookingStation> findCookingStationByIp(String ip);
}
