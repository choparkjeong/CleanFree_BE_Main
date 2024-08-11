package site.cleanfree.be_main.consultant;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConsultantAccessRepository extends MongoRepository<ConsultantAccess, String> {

    Optional<ConsultantAccess> findConsultantAccessByIp(String ip);
}
