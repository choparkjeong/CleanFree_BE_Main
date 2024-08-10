package site.cleanfree.be_main.consultant;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConsultantRepository extends MongoRepository<Consultant, String> {

    Optional<Consultant> findConsultantByIp(String ip);
}
