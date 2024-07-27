package site.cleanfree.be_main.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.cleanfree.be_main.diary.state.SkinStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDiaryListDto {
    private String diaryId;
    private String thumbnailUrl;
    private String dayDifference;
    private String writeTime;
    private SkinStatus skinStatus;

    public void setThumbnailUrl(String defaultThumbnailUrl) {
        this.thumbnailUrl = defaultThumbnailUrl;
    }

    public void setDayDifference(String dayDifference) {
        this.dayDifference = dayDifference;
    }

    public void setWriteTime(String writeTime) {
        this.writeTime = writeTime;
    }
}
