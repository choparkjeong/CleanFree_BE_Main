package site.cleanfree.be_main.diary.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import site.cleanfree.be_main.diary.domain.Diary;

@Repository
public interface DiaryRepository extends MongoRepository<Diary, String> {

}
