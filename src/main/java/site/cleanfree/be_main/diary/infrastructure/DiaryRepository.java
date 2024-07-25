package site.cleanfree.be_main.diary.infrastructure;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import site.cleanfree.be_main.diary.domain.Diary;


public interface DiaryRepository extends MongoRepository<Diary, String> {

    Optional<Diary> getDiaryByMemberUuidAndDiaryId(String memberUuid, String diaryId);
}
