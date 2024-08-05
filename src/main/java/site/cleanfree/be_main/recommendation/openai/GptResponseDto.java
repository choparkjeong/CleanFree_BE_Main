package site.cleanfree.be_main.recommendation.openai;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.cleanfree.be_main.recommendation.domain.Solution;

@Getter
@NoArgsConstructor
public class GptResponseDto {

    private List<String> ingredients;
    private List<Solution> solutions;

    @Builder
    public GptResponseDto(List<String> ingredients, List<Solution> solutions) {
        this.ingredients = ingredients;
        this.solutions = solutions;
    }

}
