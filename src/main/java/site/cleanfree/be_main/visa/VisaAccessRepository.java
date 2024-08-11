package site.cleanfree.be_main.visa;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VisaAccessRepository extends MongoRepository<VisaAccess, String> {

    Optional<VisaAccess> findVisaAccessByIp(String ip);
}
