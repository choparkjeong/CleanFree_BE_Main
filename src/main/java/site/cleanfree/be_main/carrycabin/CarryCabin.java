package site.cleanfree.be_main.carrycabin;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import site.cleanfree.be_main.common.MongoBaseTimeEntity;

@Getter
@Document(collection = "carry_cabin")
public class CarryCabin extends MongoBaseTimeEntity {

    @Id
    private String id;
    private String name;
    private String email;

    @Builder
    public CarryCabin(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
