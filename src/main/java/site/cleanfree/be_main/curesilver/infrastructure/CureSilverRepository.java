package site.cleanfree.be_main.curesilver.infrastructure;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import site.cleanfree.be_main.curesilver.domain.CureSilver;

public interface CureSilverRepository extends MongoRepository<CureSilver, String> {

}
