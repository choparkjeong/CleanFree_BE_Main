package site.cleanfree.be_main.curesilver;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CureSilverAccessRepository extends MongoRepository<CureSilverAccess, String> {

    Optional<CureSilverAccess> findCureSilverAccessByIp(String ip);
}
