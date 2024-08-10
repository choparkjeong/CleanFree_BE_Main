package site.cleanfree.be_main.createvalue.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;
import site.cleanfree.be_main.createvalue.domain.Createvalue;

public interface CreatevalueRepository extends MongoRepository<Createvalue, String> {

}
