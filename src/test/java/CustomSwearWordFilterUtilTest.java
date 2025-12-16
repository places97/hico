import kr.go.util.CustomSwearWordFilterUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// JUnit 5를 사용한다고 가정합니다.
class CustomSwearWordFilterUtilTest {

  private CustomSwearWordFilterUtil filter;

  @BeforeEach
  void setUp() {
    // 테스트 시작 전에 필터 유틸리티를 초기화합니다.
    filter = new CustomSwearWordFilterUtil();
  }

  @Test
  void testNoSwearWords() {
    String text = "오늘 날씨가 정말 좋습니다.";
    String result = filter.findSwearWords(text);

    // 비속어가 없으므로 결과는 빈 문자열이어야 합니다.
    assertTrue(result.isEmpty(), "비속어가 없어야 합니다.");
  }

  @Test
  void testBasicSwearWords() {
    String text = "이건 진짜 개새끼이며 씨팔이다.";
    String result = filter.findSwearWords(text);

    // 검출된 단어가 예상 목록과 일치하는지 확인합니다. (순서는 HashSet 때문에 보장되지 않으므로 contains를 사용하거나 Set으로 비교)
    // 여기서는 간단히 문자열 포함 여부로 확인합니다.
    assertTrue(result.contains("개새끼"), "개새끼가 검출되어야 합니다.");
    assertTrue(result.contains("씨팔"), "씨팔이 검출되어야 합니다.");
  }

  @Test
  void testIgnoreCaseAndMixedWords() {
    String text = "저리가 FucK c파 18년아";
    String result = filter.findSwearWords(text);

    // 대소문자 무시 (fuck -> FucK)
    assertTrue(result.contains("FucK"), "대소문자 무시(FucK)가 검출되어야 합니다.");
    // 혼합어 검사
    assertTrue(result.contains("c파"), "혼합어(c파)가 검출되어야 합니다.");
    assertTrue(result.contains("18년"), "숫자+한글(18년)이 검출되어야 합니다.");
  }

  @Test
  void testLongestWordPriorityAndSkip() {
    String text = "개새끼야 개로 시작하는 욕이 많네.";
    String result = filter.findSwearWords(text);

    // "개새끼"가 검출되어야 하며, 중복된 "개"는 검출되지 않아야 합니다.
    // 유틸리티 로직에서 긴 단어("개새끼")를 찾고 인덱스를 건너뛰므로 "개"와 같은 짧은 단어는 무시됩니다.
    assertEquals("개새끼", result, "가장 긴 단어가 정확히 검출되어야 합니다.");
  }
}