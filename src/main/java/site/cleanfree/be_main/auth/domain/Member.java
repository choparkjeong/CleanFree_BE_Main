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
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @Column(name = "email", nullable = false, length = 30)
    private String email;
    @Column(name = "name", nullable = false, length = 20)
    private String name;
    @Column(name = "phone_num", nullable = false, length = 20)
    private String phoneNum;
    @Column(name = "sex", nullable = false, length = 20)
    private String sex;
    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Builder
    public Member(Long id, String email, String name, String phoneNum, String sex, String uuid) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phoneNum = phoneNum;
        this.sex = sex;
        this.uuid = uuid;
    }

    public static Member converter(MemberSnsLoginRequestDto memberSnsLoginRequestDto) {
        return Member.builder()
                .email(memberSnsLoginRequestDto.getEmail())
                .name(memberSnsLoginRequestDto.getName())
                .phoneNum(memberSnsLoginRequestDto.getPhoneNumber())
                .sex(memberSnsLoginRequestDto.getSex())
                .uuid(memberSnsLoginRequestDto.getUuid())
                .build();
    }
}
