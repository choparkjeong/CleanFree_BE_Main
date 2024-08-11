package site.cleanfree.be_main.cozyquick.domain;

import jakarta.persistence.Id;
import jakarta.persistence.Index;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import site.cleanfree.be_main.common.MongoBaseTimeEntity;

@Getter
@Document(collection = "cozy_quick_access")
public class CozyquickAccess extends MongoBaseTimeEntity {

    @Id
    private String id;
    @Indexed(unique = true)
    private String ip;

    @Builder
    public CozyquickAccess(String id, String ip) {
        this.id = id;
        this.ip = ip;
    }
}
