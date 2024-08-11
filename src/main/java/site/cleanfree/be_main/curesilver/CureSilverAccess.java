package site.cleanfree.be_main.curesilver;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import site.cleanfree.be_main.common.MongoBaseTimeEntity;

@Getter
@Document(collection = "cure_silver_access")
public class CureSilverAccess extends MongoBaseTimeEntity {

    @Id
    private String id;
    @Indexed
    private String ip;

    @Builder
    public CureSilverAccess(String id, String ip) {
        this.id = id;
        this.ip = ip;
    }
}
