package site.cleanfree.be_main.recommendation.domain;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class Reference {

    private List<String> youtube;
    private List<String> blog;
    private List<String> etc;
}
