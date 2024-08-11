package site.cleanfree.be_main.carrycabin;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarryCabinAccessRepository extends MongoRepository<CarryCabinAccess, String> {

    Optional<CarryCabinAccess> findCarryCabinAccessByIp(String ip);
}
