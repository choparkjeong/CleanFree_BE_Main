package site.cleanfree.be_main.cozyquick.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;
import site.cleanfree.be_main.cozyquick.domain.Cozyquick;

public interface CozyquickRepository extends MongoRepository<Cozyquick, String> {

}
