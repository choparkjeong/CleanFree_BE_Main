package site.cleanfree.be_main.cozyhouse.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import site.cleanfree.be_main.common.MongoBaseTimeEntity;

@Getter
@Document(collection = "cozy_house")
public class CozyHouse extends MongoBaseTimeEntity {

    @Id
    private String id;
    @Indexed(unique = true)//TODO: IP로 중복신청 거를건지
    private String ip;
    private String name;
    private String phoneNumber;


    @Builder
    public CozyHouse(String id, String ip, String name, String phoneNumber) {
        this.id = id;
        this.ip = ip;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
