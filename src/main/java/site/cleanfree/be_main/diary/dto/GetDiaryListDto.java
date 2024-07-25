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
    private String id;
    private String thumbnail;
    private String dayDifference;
    private String createdDate;
    private SkinStatus skinStatus;
}
