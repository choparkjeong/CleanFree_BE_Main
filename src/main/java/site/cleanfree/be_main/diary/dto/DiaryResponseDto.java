package site.cleanfree.be_main.diary.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import site.cleanfree.be_main.diary.state.SkinStatus;

@Getter
@Setter
@Builder
public class DiaryResponseDto {

    private String diaryId;
    private SkinStatus skinStatus;
    private String thumbnailUrl;
    private List<String> cosmetics;
    private boolean isAlcohol;
    private boolean isExercise;
    private String sleepTime;
    private String memo;
    private LocalDate writeTime;
}
