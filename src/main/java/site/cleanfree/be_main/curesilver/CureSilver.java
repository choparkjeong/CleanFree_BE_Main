package site.cleanfree.be_main.curesilver;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import site.cleanfree.be_main.common.MongoBaseTimeEntity;

@Getter
@Document(collection = "cure_silver")
public class CureSilver extends MongoBaseTimeEntity {

    @Id
    private String id;
    @Indexed(unique = true)
    private String ip;

    @Builder
    public CureSilver(String id, String ip) {
        this.id = id;
        this.ip = ip;
    }

}
