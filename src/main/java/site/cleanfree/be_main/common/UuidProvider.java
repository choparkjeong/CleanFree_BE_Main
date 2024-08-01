package site.cleanfree.be_main.common;

import java.util.Random;

public class UuidProvider {

    private static final int DIARY_ID_LENGTH = 10;
    private static final int RECOMMENDATION_RESULT_ID_LENGTH = 10;

    // uuid 생성(숫자 5개, 알파벳 소문자 5개 조합)
    public static String generateMemberUuid() {
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

    public static String generateDiaryUuid() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < DIARY_ID_LENGTH; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public static String generateRecommendationResultId() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(RECOMMENDATION_RESULT_ID_LENGTH);

        for (int i = 0; i < RECOMMENDATION_RESULT_ID_LENGTH; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
