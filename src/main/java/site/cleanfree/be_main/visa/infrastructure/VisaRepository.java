package site.cleanfree.be_main.visa.infrastructure;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import site.cleanfree.be_main.visa.domain.Visa;

public interface VisaRepository extends MongoRepository<Visa, String> {

    Optional<Visa> findVisaByIp(String ip);
}
