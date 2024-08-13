package site.cleanfree.be_main.cozyhouse.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.cleanfree.be_main.common.BaseResponse;
import site.cleanfree.be_main.common.exception.ErrorStatus;
import site.cleanfree.be_main.cozyhouse.domain.CozyHouse;
import site.cleanfree.be_main.cozyhouse.domain.CozyHouseAccess;
import site.cleanfree.be_main.cozyhouse.infrastructure.CozyHouseAccessRepository;
import site.cleanfree.be_main.cozyhouse.infrastructure.CozyHouseRepository;
import site.cleanfree.be_main.cozyhouse.vo.CozyHouseRegisterRequestVo;

@Service
@RequiredArgsConstructor
public class CozyHouseService {

    private final CozyHouseRepository cozyHouseRepository;
    private final CozyHouseAccessRepository cozyHouseAccessRepository;

    public BaseResponse<Object> register(CozyHouseRegisterRequestVo cozyHouseRegisterRequestVo) {
        try {
            cozyHouseRepository.save(CozyHouse.builder()
                .name(cozyHouseRegisterRequestVo.name())
                .phoneNumber(cozyHouseRegisterRequestVo.phoneNumber())
                .build());
            return BaseResponse.builder()
                .success(true)
                .errorCode(null)
                .message("success to save data")
                .data(null)
                .build();
        } catch (Exception e) {
            return BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("fail to save data")
                .data(e.getMessage())
                .build();
        }
    }

    public BaseResponse<Object> access(String clientIp) {
        Optional<CozyHouseAccess> cozyHouseAccessOpt = cozyHouseAccessRepository.findCozyHouseAccessByIp(
            clientIp);

        if (cozyHouseAccessOpt.isPresent()) {
            CozyHouseAccess cozyHouseAccess = cozyHouseAccessOpt.get();
            cozyHouseAccessRepository.save(CozyHouseAccess.builder()
                .id(cozyHouseAccess.getId())
                .ip(cozyHouseAccess.getIp())
                .count(cozyHouseAccess.getCount() + 1)
                .build());
            return BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.DATA_PERSIST_ERROR.getCode())
                .message("already existed ip. update count.")
                .build();
        }

        cozyHouseAccessRepository.save(CozyHouseAccess.builder()
            .ip(clientIp)
            .count(1)
            .build());

        return BaseResponse.builder()
            .success(true)
            .errorCode(null)
            .message("success to save ip.")
            .data(null)
            .build();
    }
}
