package site.cleanfree.be_main.recommendation.application;

import java.util.List;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.recommendation.dto.ResultListResponseDto;
import site.cleanfree.be_main.recommendation.dto.ResultResponseDto;
import site.cleanfree.be_main.recommendation.vo.QuestionVo;

public interface RecommendationService {

    BaseResponse<?> search(String authorization, QuestionVo questionVo);

    BaseResponse<ResultResponseDto> getResult(String authorization, String resultId);

    BaseResponse<List<ResultListResponseDto>> getResults(String authorization);

}
