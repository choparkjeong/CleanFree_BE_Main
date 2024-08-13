package site.cleanfree.be_main.curesilver.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import site.cleanfree.be_main.common.MongoBaseTimeEntity;

@Getter
@Document(collection = "cure_silver")
public class CureSilver extends MongoBaseTimeEntity {

    @Id
    private String id;
    private String name;
    private String phoneNumber;

    @Builder
    public CureSilver(String id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

}
