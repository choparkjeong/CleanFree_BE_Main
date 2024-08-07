package site.cleanfree.be_main.recommendation.infrastructure;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import site.cleanfree.be_main.recommendation.domain.Recommendation;

public interface RecommendationRepository extends MongoRepository<Recommendation, String> {

    Optional<Recommendation> getRecommendationByResultId(String resultId);

    List<Recommendation> getAllByMemberUuid(String memberUuid);

    Optional<Recommendation> findTopByMemberUuidOrderByCreatedAtDesc(String memberUuid);
}
