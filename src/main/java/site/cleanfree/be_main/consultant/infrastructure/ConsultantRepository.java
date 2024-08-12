package site.cleanfree.be_main.consultant.infrastructure;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import site.cleanfree.be_main.consultant.domain.Consultant;

public interface ConsultantRepository extends MongoRepository<Consultant, String> {

    Optional<Consultant> findConsultantByIp(String ip);
}
