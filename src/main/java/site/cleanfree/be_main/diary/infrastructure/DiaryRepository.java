package site.cleanfree.be_main.diary.infrastructure;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import site.cleanfree.be_main.diary.domain.Diary;
import site.cleanfree.be_main.diary.dto.GetDiaryListDto;

@Repository
public interface DiaryRepository extends MongoRepository<Diary, String> {

    Optional<Diary> getDiaryByMemberUuidAndDiaryId(String memberUuid, String diaryId);
    List<GetDiaryListDto> findAllByMemberUuidOrderByWriteTimeDesc(String uuid);

    Optional<Diary> findTopByMemberUuidOrderByWriteTimeDesc(String memberUuid);

    Optional<Diary> findDiaryByMemberUuidAndWriteTime(String memberUuid, LocalDate writeTime);
}
