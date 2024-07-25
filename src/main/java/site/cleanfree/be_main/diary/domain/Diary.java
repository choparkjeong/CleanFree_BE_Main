package site.cleanfree.be_main.diary.domain;

import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import site.cleanfree.be_main.common.MongoBaseTimeEntity;
import site.cleanfree.be_main.diary.state.SkinStatus;

@NoArgsConstructor
@Getter
@ToString
@Document(collection = "diary")
public class Diary extends MongoBaseTimeEntity {

    @Id
    private String id;
    private String diaryId;
    private String memberUuid;
    private SkinStatus skinStatus;
    private String thumbnailUrl;
    private List<String> cosmetics;
    private boolean isAlcohol;
    private boolean isExercise;
    private String sleepTime;
    private String memo;
    private LocalDateTime writeTime;

    @Builder
    public Diary(
        String id,
        String diaryId,
        String memberUuid,
        SkinStatus skinStatus,
        String thumbnailUrl,
        List<String> cosmetics,
        boolean isAlcohol,
        boolean isExercise,
        String sleepTime,
        String memo,
        LocalDateTime writeTime //KST로 저장됨
    ) {
        this.id = id;
        this.diaryId = diaryId;
        this.skinStatus = skinStatus;
        this.memberUuid = memberUuid;
        this.thumbnailUrl = thumbnailUrl;
        this.cosmetics = cosmetics;
        this.isAlcohol = isAlcohol;
        this.isExercise = isExercise;
        this.sleepTime = sleepTime;
        this.memo = memo;
        this.writeTime = writeTime;
    }
}
