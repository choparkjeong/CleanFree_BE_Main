package site.cleanfree.be_main.auth.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberSnsLoginRequestDto {

    private String snsId;
//    private String snsType;
//    private String email;
//    private String name;
//    private String sex;
//    private String phoneNumber;
    private String uuid;

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
