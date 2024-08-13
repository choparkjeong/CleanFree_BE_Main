package site.cleanfree.be_main.createeasy.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import site.cleanfree.be_main.common.MongoBaseTimeEntity;

@Getter
@Document(collection = "create_easy")
public class CreateEasy extends MongoBaseTimeEntity {

    @Id
    private String id;
    private String name;
    private String phoneNumber;

    @Builder
    public CreateEasy(String id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
