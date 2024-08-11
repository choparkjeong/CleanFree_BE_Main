package site.cleanfree.be_main.cozyquick.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import site.cleanfree.be_main.common.MongoBaseTimeEntity;

@Getter
@Document(collection = "cozy_quick")
public class Cozyquick extends MongoBaseTimeEntity {

    @Id
    private String id;
    @Indexed(unique = true)
    private String ip;
    private String search;

    @Builder
    public Cozyquick(String id, String ip, String search) {
        this.id = id;
        this.ip = ip;
        this.search = search;
    }
}
