package site.cleanfree.be_main.recommendation.infrastructure;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import site.cleanfree.be_main.recommendation.domain.Recommendation;

@Repository
public interface ReactiveRecommendationRepository extends ReactiveMongoRepository<Recommendation, String> {

}
