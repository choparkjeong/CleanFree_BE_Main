package site.cleanfree.be_main.recommendation.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class Review {

    private String score;
    private String summary;

}
