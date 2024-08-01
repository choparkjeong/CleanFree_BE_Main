package site.cleanfree.be_main.recommendation.presentation;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.recommendation.application.RecommendationService;
import site.cleanfree.be_main.recommendation.dto.ResultResponseDto;
import site.cleanfree.be_main.recommendation.vo.QuestionVo;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "맞춤 화장품 검색 API", description = "맞춤 화장품 검색 API")
@RequestMapping("/api/v1/search")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @PostMapping
    public ResponseEntity<BaseResponse<?>> search(
        @RequestHeader String Authorization,
        @RequestBody QuestionVo questionVo
    ) {
        return ResponseEntity.ok(recommendationService.search(Authorization, questionVo));
    }

    @GetMapping("/result/{id}")
    public ResponseEntity<BaseResponse<ResultResponseDto>> getSearchResult(
        @RequestHeader String Authorization,
        @PathVariable String id
    ) {
        return ResponseEntity.ok(recommendationService.getResult(Authorization, id));
    }

}
