package site.cleanfree.be_main.visa;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VisaRepository extends MongoRepository<Visa, String> {

    Optional<Visa> findVisaByIp(String ip);
}
