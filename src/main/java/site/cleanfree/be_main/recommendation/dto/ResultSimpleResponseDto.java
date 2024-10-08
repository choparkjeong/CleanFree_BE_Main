package site.cleanfree.be_main.recommendation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResultSimpleResponseDto {

    private String resultId;
    private String question;
    private String dayDifference;
    private boolean isAnalyze;

    public boolean getIsAnalyze() {
        return this.isAnalyze;
    }

    public void setIsAnalyze(boolean isAnalyze) {
        this.isAnalyze = isAnalyze;
    }
}
