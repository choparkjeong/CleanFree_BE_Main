package site.cleanfree.be_main.visa.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import site.cleanfree.be_main.common.MongoBaseTimeEntity;

@Getter
@Document(collection = "visa")
public class Visa extends MongoBaseTimeEntity {

    @Id
    private String id;
    @Indexed(unique = true)
    private String ip;
    private String name;
    private String phoneNumber;

    @Builder
    public Visa(String id, String ip, String name, String phoneNumber) {
        this.id = id;
        this.ip = ip;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
