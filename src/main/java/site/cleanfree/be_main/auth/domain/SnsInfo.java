package site.cleanfree.be_main.auth.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.cleanfree.be_main.auth.dto.MemberSnsLoginRequestDto;

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

    public static SnsInfo converter(MemberSnsLoginRequestDto memberSnsLoginRequestDto, String uuid) {
        return SnsInfo.builder()
//                .snsType(memberSnsLoginRequestDto.getSnsType())
                .snsId(memberSnsLoginRequestDto.getSnsId())
                .uuid(memberSnsLoginRequestDto.getUuid())
                .build();
    }
}
