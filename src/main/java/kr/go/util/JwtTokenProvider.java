package kr.go.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import kr.go.hico.cmm.security.svc.PrincipalSvc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
  private final Key key;
  private final PrincipalSvc principalSvc;

  // security.properties에서 시크릿 키 주입
  public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, PrincipalSvc  principalSvc) {
    this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    this.principalSvc = principalSvc;
  }

  // JWT 토큰 생성
  public String generateToken(Authentication authentication) {
    String username = authentication.getName();
    Date now = new Date();
    // 토큰 만료 시간 설정
    Date expiryDate = new Date(now.getTime() + (1000 * 60 * 60 * 3));

    return Jwts.builder()
        .setSubject(username) // 사용자 식별자
        .setIssuedAt(now)     // 발행일
        .setExpiration(expiryDate) // 만료일
        .signWith(key, SignatureAlgorithm.HS256) // 서명
        .compact();
  }

  // JWT 토큰에서 사용자 이름(ID) 추출
  public String getUsernameFromJWT(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  // JWT 토큰 유효성 검증
  public boolean validateToken(String authToken) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
      return true;
    } catch (SecurityException | MalformedJwtException ex) {
      // 유효하지 않은 JWT 서명
    } catch (ExpiredJwtException ex) {
      // 만료된 JWT 토큰
    } catch (UnsupportedJwtException ex) {
      // 지원되지 않는 JWT 토큰
    } catch (IllegalArgumentException ex) {
      // JWT Claims 문자열이 비어있음
    }
    return false;
  }

  // JWT 토큰으로부터 인증 객체 생성
  public Authentication getAuthentication(String token) {
    String username = getUsernameFromJWT(token);
    UserDetails userDetails = principalSvc.loadUserByUsername(username);
    // AuthenticationManager 없이 인증 객체를 직접 생성
    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }
}
