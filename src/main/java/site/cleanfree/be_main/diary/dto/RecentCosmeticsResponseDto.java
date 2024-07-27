package site.cleanfree.be_main.diary.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RecentCosmeticsResponseDto {

    private String thumbnailUrl;
    private List<String> cosmetics;
    private LocalDate writeTime;
}
