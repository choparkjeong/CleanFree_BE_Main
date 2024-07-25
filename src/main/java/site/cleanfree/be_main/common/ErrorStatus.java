package site.cleanfree.be_main.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus {
    SUCCESS(1000),
    JWT_ERROR(1001),
    DATA_PERSIST_ERROR(1002),
    DUPLICATED_CATEGORY_ERROR(1003),
    ;

    private final int code;
}