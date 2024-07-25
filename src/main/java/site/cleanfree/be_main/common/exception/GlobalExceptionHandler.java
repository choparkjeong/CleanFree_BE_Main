package site.cleanfree.be_main.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import site.cleanfree.be_main.common.BaseResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        // Check if the error is related to SkinStatus enum
        if (ex.getMessage().contains("SkinStatus")) {
            return ResponseEntity.ok(BaseResponse.builder()
                    .success(false)
                    .errorCode(ErrorStatus.INCORRECT_SKIN_STATUS.getCode())
                    .message("Incorrect skin status. Accepted values are: GOOD, NORMAL, BAD")
                    .data(null)
                    .build());
        }

        // Handle other HttpMessageNotReadableException cases
        return ResponseEntity.ok(BaseResponse.builder()
                .success(false)
                .errorCode(ErrorStatus.BAD_DATA.getCode())
                .message("Invalid request body")
                .data(null)
                .build());
    }
}
