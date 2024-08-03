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
import site.cleanfree.be_main.auth.vo.MemberSignupRequestVo;
import site.cleanfree.be_main.common.JpaBaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends JpaBaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @Column(name = "email", nullable = false, length = 50)
    private String email;
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "gender", nullable = false, length = 20)
    private String gender;
    @Column(name = "uuid", nullable = false, length = 10)
    private String uuid;
    @Column(name = "age", nullable = false, length = 3)
    private String age;

    @Builder
    public Member(Long id, String email, String name, String gender, String uuid, String age) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.uuid = uuid;
        this.age = age;
    }

    public static Member converter(MemberSignupRequestVo memberSignupRequestVo, String memberUuid) {
        return Member.builder()
            .email(memberSignupRequestVo.getEmail())
            .name(memberSignupRequestVo.getName())
            .gender(memberSignupRequestVo.getGender())
            .age(memberSignupRequestVo.getAge())
            .uuid(memberUuid)
            .build();
    }
}
