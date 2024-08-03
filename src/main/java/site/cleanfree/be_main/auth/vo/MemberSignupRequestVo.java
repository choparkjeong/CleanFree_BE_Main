package site.cleanfree.be_main.auth.vo;

import lombok.Getter;

@Getter
public class MemberSignupRequestVo {

    private String snsId;
    private String name;
    private String gender;
    private String birthDate;
    private String email;
}
