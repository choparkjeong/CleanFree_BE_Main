package site.cleanfree.be_main.cozyhouse.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import site.cleanfree.be_main.common.MongoBaseTimeEntity;

@Getter
@Document(collection = "cozy_house_access")
public class CozyHouseAccess extends MongoBaseTimeEntity {

    @Id
    private String id;
    @Indexed(unique = true)
    private String ip;
    private int count;

    @Builder
    public CozyHouseAccess(String id, String ip, int count) {
        this.id = id;
        this.ip = ip;
        this.count = count;
    }
}
