package site.cleanfree.be_main.recommendation.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchLimit {
    ONE_PER_DAY(1);

    private final Integer count;
}
