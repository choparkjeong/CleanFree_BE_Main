package site.cleanfree.be_main.cookingstation;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "cooking_station_access")
public class CookingStationAccess {

    @Id
    private String id;
    @Indexed(unique = true)
    private String ip;

    @Builder
    public CookingStationAccess(String id, String ip) {
        this.id = id;
        this.ip = ip;
    }

}
