package site.cleanfree.be_main.recommendation.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class Cosmetic {

    private String cosmetic;
    private String image;
    private String costRange;
    private String url;
    private Review maxReview;
    private Review minReview;

}
