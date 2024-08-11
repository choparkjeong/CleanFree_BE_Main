package site.cleanfree.be_main.carrycabin;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import site.cleanfree.be_main.common.MongoBaseTimeEntity;

@Getter
@Document(collection = "carry_cabin_access")
public class CarryCabinAccess extends MongoBaseTimeEntity {

    @Id
    private String id;
    @Indexed(unique = true)
    private String ip;

    @Builder
    public CarryCabinAccess(String id, String ip) {
        this.id = id;
        this.ip = ip;
    }
}
