package site.cleanfree.be_main.consultant;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "consultant_access")
public class ConsultantAccess {

    @Id
    private String id;
    @Indexed(unique = true)
    private String ip;

    @Builder
    public ConsultantAccess(String id, String ip) {
        this.id = id;
        this.ip = ip;
    }
}
