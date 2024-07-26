package site.cleanfree.be_main.diary.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import site.cleanfree.be_main.diary.state.SkinStatus;

@Getter
@Setter
@Builder
public class DiaryWriteRequestDto {

    private SkinStatus skinStatus;
    private String thumbnailUrl;
    private List<String> cosmetics;
    private boolean isAlcohol;
    private boolean isExercise;
    private String sleepTime;
    private String memo;
    private String writeTime;
}
