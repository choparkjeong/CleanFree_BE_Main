package site.cleanfree.be_main.cozyhouse;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import site.cleanfree.be_main.common.MongoBaseTimeEntity;

@Getter
@Document(collection = "cozy_house")
public class CozyHouse extends MongoBaseTimeEntity {

    @Id
    private String id;
    @Indexed(unique = true)
    private String ip;

    @Builder
    public CozyHouse(String id, String ip) {
        this.id = id;
        this.ip = ip;
    }
}
