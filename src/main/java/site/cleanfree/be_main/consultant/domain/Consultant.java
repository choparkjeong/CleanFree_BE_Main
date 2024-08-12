package site.cleanfree.be_main.consultant.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import site.cleanfree.be_main.common.MongoBaseTimeEntity;

@Getter
@Document(collection = "consultant")
public class Consultant extends MongoBaseTimeEntity {

    @Id
    private String id;
    @Indexed(unique = true)
    private String ip;
    private String name;
    private String contact;

    @Builder
    public Consultant(String id, String ip, String name, String contact) {
        this.id = id;
        this.ip = ip;
        this.name = name;
        this.contact = contact;
    }
}
