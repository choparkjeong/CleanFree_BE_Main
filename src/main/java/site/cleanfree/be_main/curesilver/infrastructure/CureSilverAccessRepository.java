package site.cleanfree.be_main.curesilver.infrastructure;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import site.cleanfree.be_main.curesilver.domain.CureSilverAccess;

public interface CureSilverAccessRepository extends MongoRepository<CureSilverAccess, String> {

    Optional<CureSilverAccess> findCureSilverAccessByIp(String ip);
}
