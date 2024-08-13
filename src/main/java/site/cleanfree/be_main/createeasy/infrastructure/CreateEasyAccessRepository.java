package site.cleanfree.be_main.createeasy.infrastructure;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import site.cleanfree.be_main.createeasy.domain.CreateEasyAccess;

public interface CreateEasyAccessRepository extends MongoRepository<CreateEasyAccess, String> {

    Optional<CreateEasyAccess> findCreateEasyAccessByIp(String ip);
}
