package site.cleanfree.be_main.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus {
    SUCCESS(1000),
    JWT_ERROR(1001),
    DATA_PERSIST_ERROR(1002),
    DUPLICATED_CATEGORY_ERROR(1003),
    NOT_EXISTED_ERROR(1004),
    INCORRECT_SKIN_STATUS(1005),
    DATA_UPDATE_ERROR(1006),
    BAD_DATA(1007)
    ;

    private final int code;
}
