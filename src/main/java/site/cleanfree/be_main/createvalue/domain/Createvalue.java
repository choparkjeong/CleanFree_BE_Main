package site.cleanfree.be_main.createvalue.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import site.cleanfree.be_main.common.MongoBaseTimeEntity;

@Getter
@Document(collection = "createvalue")
public class Createvalue extends MongoBaseTimeEntity {

    @Id
    private String id;
    @Indexed(unique = true)
    private String searchId;
    private String search;

    @Builder
    public Createvalue(String id, String searchId, String search) {
        this.id = id;
        this.searchId = searchId;
        this.search = search;
    }
}
