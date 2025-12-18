package kr.go.logging;

import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.rewrite.RewritePolicy;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.message.SimpleMessage;

/**
 * <pre>
 * 간략 설명 : 로그 개인정보 자동 마스킹 정책 (Log4j2 RewritePolicy)
 * 상세 설명 :
 *  1. 로그 출력(Appender) 직전 메시지를 가로채 정규표현식 기반으로 민감정보를 비식별화함.
 *  2. 주민등록번호, 연락처(휴대폰/유선), 이메일, 비밀번호 등 주요 4대 민감항목 자동 감지.
 *  3. 개발자/관리자의 실수로 인한 로그 파일 내 개인정보 평문 노출을 원천 차단하는 최후 보루 아키텍처.
 *  4. 2025년 개인정보 기술적·관리적 보호조치 준거성 및 보안 감사 대응용 공통 컴포넌트.
 * </pre>
 */
@Plugin(name = "LogMaskingPolicy", category = Core.CATEGORY_NAME, elementType = "rewritePolicy", printObject = true)
public class LogMaskingPolicy implements RewritePolicy {

  @Override
  public LogEvent rewrite(LogEvent event) {
    String message = event.getMessage().getFormattedMessage();
    if (message == null || message.isEmpty()) return event;

    // 2025년 보안 감사 핵심 4종
    String maskedMessage = message
        // 주민번호
        .replaceAll("\\d{6}-[1-4]\\d{6}", "******-*******")
        // 전화번호 및 휴대폰
        // (지역/휴대폰번호)-(중간번호)-(끝번호) 구조에서 중간번호를 ****로 치환
        .replaceAll("(\\d{2,3})-(\\d{3,4})-(\\d{4})", "$1-****-$3")
        // 비밀번호(대소문자 무시)
        .replaceAll("(?i)password\\s*=\\s*[^\\s,]+", "password=********")
        // 이메일 (ab***@naver.com)
        .replaceAll("([^@]{2})[^@]+(@.*)", "$1***$2");

    return new Log4jLogEvent.Builder(event)
        .setMessage(new SimpleMessage(maskedMessage))
        .build();
  }

  @PluginFactory
  public static LogMaskingPolicy createPolicy() {
    return new LogMaskingPolicy();
  }
}