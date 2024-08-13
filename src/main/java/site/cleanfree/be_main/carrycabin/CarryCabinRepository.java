package site.cleanfree.be_main.carrycabin;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarryCabinRepository extends MongoRepository<CarryCabin, String> {
}
