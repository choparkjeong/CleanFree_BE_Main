package site.cleanfree.be_main.curesilver;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CureSilverRepository extends MongoRepository<CureSilver, String> {

    Optional<CureSilver> findCureSilverByIp(String ip);
}
