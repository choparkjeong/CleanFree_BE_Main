package site.cleanfree.be_main.cookingstation;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import site.cleanfree.be_main.common.MongoBaseTimeEntity;

@Getter
@Document(collection = "cooking_station_access")
public class CookingStationAccess extends MongoBaseTimeEntity {

    @Id
    private String id;
    @Indexed(unique = true)
    private String ip;
    private int count;

    @Builder
    public CookingStationAccess(String id, String ip, int count) {
        this.id = id;
        this.ip = ip;
        this.count = count;
    }

}
