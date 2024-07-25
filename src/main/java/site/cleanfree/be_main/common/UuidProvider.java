package site.cleanfree.be_main.common;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class UuidProvider {

    // uuid 생성(숫자 5개, 알파벳 소문자 5개 조합)
    public String generateUuid() {
        final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 5; i++) {
            sb.append(random.nextInt(10));
        }
        for (int i = 0; i < 5; i++) {
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }
}
