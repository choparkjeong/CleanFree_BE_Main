package site.cleanfree.be_main.visa.infrastructure;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import site.cleanfree.be_main.visa.domain.VisaAccess;

public interface VisaAccessRepository extends MongoRepository<VisaAccess, String> {

    Optional<VisaAccess> findVisaAccessByIp(String ip);
}
