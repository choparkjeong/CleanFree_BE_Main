package site.cleanfree.be_main.createvalue.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "create_value_access")
public class CreatevalueAccess {

    @Id
    private String id;
    @Indexed(unique = true)
    private String ip;

    @Builder
    public CreatevalueAccess(String id, String ip) {
        this.id = id;
        this.ip = ip;
    }

}
