package site.cleanfree.be_main.diary.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import site.cleanfree.be_main.diary.domain.Diary;
import site.cleanfree.be_main.diary.dto.GetDiaryListDto;

import java.util.List;

@Repository
public interface DiaryRepository extends MongoRepository<Diary, String> {

    List<GetDiaryListDto> findAllByMemberUuidOrderByCreatedAt(String uuid);
}
