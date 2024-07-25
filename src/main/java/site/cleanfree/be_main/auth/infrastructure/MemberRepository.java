package site.cleanfree.be_main.auth.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import site.cleanfree.be_main.auth.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUuid(String uuid);
}
