package site.cleanfree.be_main.auth.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import site.cleanfree.be_main.auth.domain.SnsInfo;

import java.util.Optional;

public interface SnsInfoRepository extends JpaRepository<SnsInfo, Long> {
    Optional<SnsInfo> findBySnsId(String snsId);
}
