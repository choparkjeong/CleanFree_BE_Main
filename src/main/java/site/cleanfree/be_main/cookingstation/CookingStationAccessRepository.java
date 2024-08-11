package site.cleanfree.be_main.cookingstation;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CookingStationAccessRepository extends MongoRepository<CookingStationAccess, String> {

    Optional<CookingStationAccess> findCookingStationAccessByIp(String ip);
}
