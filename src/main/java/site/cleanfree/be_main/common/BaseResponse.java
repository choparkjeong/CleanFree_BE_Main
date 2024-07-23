package site.cleanfree.be_main.common;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BaseResponse<T> {
    private final boolean success;
    private final String message;
    private final T data;

    @Builder
    public BaseResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}