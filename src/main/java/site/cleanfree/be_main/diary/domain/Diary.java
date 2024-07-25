package site.cleanfree.be_main.diary.domain;

import jakarta.persistence.Id;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import site.cleanfree.be_main.common.BaseTimeEntity;
import site.cleanfree.be_main.diary.state.SkinStatus;

@NoArgsConstructor
@Getter
@ToString
@Document(collection = "diary")
public class Diary extends BaseTimeEntity {

    @Id
    private String id;
    private String memberUuid;
    private SkinStatus skinStatus;
    private String thumbnailUrl;
    private List<String> cosmetics;
    private boolean isAlcohol;
    private boolean isExercise;
    private String sleepTime;
    private String memo;
    private String createdDate;
}
