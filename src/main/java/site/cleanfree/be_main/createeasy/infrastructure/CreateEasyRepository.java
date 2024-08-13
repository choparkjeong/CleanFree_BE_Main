package site.cleanfree.be_main.createeasy.infrastructure;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import site.cleanfree.be_main.createeasy.domain.CreateEasy;

public interface CreateEasyRepository extends MongoRepository<CreateEasy, String> {

}
