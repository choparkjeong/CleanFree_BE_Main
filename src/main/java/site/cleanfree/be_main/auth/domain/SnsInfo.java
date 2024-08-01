package site.cleanfree.be_main.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SnsInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sns_info_id")
    private Long id;
    //    @Column(name = "sns_type", nullable = false)
//    private String snsType;
    @Column(name = "sns_id", nullable = false)
    private String snsId;
    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Builder
    public SnsInfo(String snsType, String snsId, String uuid) {
//        this.snsType = snsType;
        this.snsId = snsId;
        this.uuid = uuid;
    }
}
