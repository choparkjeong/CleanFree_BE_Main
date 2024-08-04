package site.cleanfree.be_main.recommendation.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import site.cleanfree.be_main.recommendation.domain.Cosmetic;
import site.cleanfree.be_main.recommendation.domain.Reference;

@Getter
@Setter
@Builder
public class ResultResponseDto {

    private String resultId;
    private String memberUuid;
    private String question;
    private String answer;
    private List<Cosmetic> cosmetics;
    private List<String> ingredients;
    private Reference references;
    private boolean isAnalyze;

    public boolean getIsAnalyze() {
        return this.isAnalyze;
    }

    public void setIsAnalyze(boolean isAnalyze) {
        this.isAnalyze = isAnalyze;
    }
}
