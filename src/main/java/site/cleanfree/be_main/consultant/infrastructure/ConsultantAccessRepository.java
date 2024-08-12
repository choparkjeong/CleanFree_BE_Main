package site.cleanfree.be_main.consultant.infrastructure;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import site.cleanfree.be_main.consultant.domain.ConsultantAccess;

public interface ConsultantAccessRepository extends MongoRepository<ConsultantAccess, String> {

    Optional<ConsultantAccess> findConsultantAccessByIp(String ip);
}
