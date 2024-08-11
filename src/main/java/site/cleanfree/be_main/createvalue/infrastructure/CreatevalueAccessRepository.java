package site.cleanfree.be_main.createvalue.infrastructure;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import site.cleanfree.be_main.createvalue.domain.CreatevalueAccess;

public interface CreatevalueAccessRepository extends MongoRepository<CreatevalueAccess, String> {

    Optional<CreatevalueAccess> findCreatevalueAccessByIp(String ip);
}
