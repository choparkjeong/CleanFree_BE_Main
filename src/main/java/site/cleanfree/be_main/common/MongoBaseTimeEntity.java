package site.cleanfree.be_main.common;

import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
public abstract class MongoBaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdAt; //todo: mongodb에 적용 안되는 문제 해결하기

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
