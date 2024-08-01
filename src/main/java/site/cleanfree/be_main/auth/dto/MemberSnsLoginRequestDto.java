package site.cleanfree.be_main.auth.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberSnsLoginRequestDto {

    private String snsId;

}
