package site.cleanfree.be_main.common;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BaseResponse<T> {
    private final boolean success;
    private final int errorCode; // success = true 인 경우, 1000
    private final String message;
    private final T data;

    @Builder
    public BaseResponse(boolean success, int errorCode, String message, T data) {
        this.success = success;
        this.errorCode = errorCode;
        this.message = message;
        this.data = data;
    }
}