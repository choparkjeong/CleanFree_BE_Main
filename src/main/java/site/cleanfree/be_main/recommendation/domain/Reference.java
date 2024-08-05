package site.cleanfree.be_main.recommendation.domain;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class Reference {

    private List<String> youtube = List.of("https://youtube.com/channel/UCw2DFGtxYINwpL-haXRXoag?si=aUkS_vhJ_cGkvLVQ");
    private List<String> blog = List.of("https://blog.naver.com/zerotoone_1");
    private List<String> etc = List.of("https://www.instagram.com/cleanfree_/");
}
