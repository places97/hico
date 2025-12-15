package kr.go.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 활성화
public class SecurityConfig {

  @Bean // AuthenticationManager 빈 등록 (로그인 API에서 사용)
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean // 비밀번호 인코더
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * HTTP 요청에 대한 보안 필터 체인 설정 (WebSecurityConfigurerAdapter 대체)
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        // CSRF 설정 (필요에 따라 disable 또는 enable)
        //.csrf(csrf -> csrf.disable())
        // 세션 기반 방식에서 POST 요청을 보낼 때 필수적인 CSRF 토큰을 숨겨진 필드로 헤더에 포함
        //csrf 제외
        .csrf(csrf -> csrf
            // SSO 콜백 URL이나 외부에서 POST 요청이 들어오는 특정 URL을 제외
            .ignoringRequestMatchers(new AntPathRequestMatcher("/login.do"))
            //.ignoringRequestMatchers(new AntPathRequestMatcher("/sso/url"))
        )
        /* 💡 세션 사용 설정
        .sessionManagement(session -> session
            .sessionFixation(sessionFixation -> sessionFixation.migrateSession()) // 권장 기본값
            // .maximumSessions(1) // 1명만 동시 접속 허용
            // .maxSessionsPreventsLogin(true) // 동시 접속 시 기존 사용자 로그아웃 처리
        )
        */
        // URL 별 권한 설정
        .authorizeHttpRequests(authorize -> authorize
            // 메인화면..개발용
            .requestMatchers(new AntPathRequestMatcher("/home.do")).permitAll()
            // 관리자
            .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")
            // 로그인
            .requestMatchers(new AntPathRequestMatcher("/login/**")).permitAll()
            // 외부망
            //.requestMatchers(new AntPathRequestMatcher("/public/**")).permitAll()
            // 마이페이지
            // .requestMatchers(new AntPathRequestMatcher("/mypage/**")).hasRole("USER")
            // 정적 리소스 및 Swagger
            .requestMatchers(
                new AntPathRequestMatcher("/**/*"), //TEST
                new AntPathRequestMatcher("/css/**"),
                new AntPathRequestMatcher("/js/**"),
                new AntPathRequestMatcher("/images/**"),
                new AntPathRequestMatcher("/")
            ).permitAll()

            // 그 외 모든 요청은 인증된 사용자만 접근 가능
            .anyRequest().authenticated()
            //.anyRequest().denyAll()
        )

        //폼 기반 로그인 설정
        .formLogin(formLogin -> formLogin
            .loginPage("/login/loginForm.do") // 커스텀 로그인 페이지 URL 지정 (컨트롤러 매핑 필요)
            .loginProcessingUrl("/login.do") // 실제 로그인 처리 요청 URL (시큐리티가 처리)
            .defaultSuccessUrl("/home.do") // 로그인 성공 후 이동할 페이지
            .failureUrl("/login/loginForm.do?error") // 로그인 실패 시 이동할 페이지
            .permitAll()
        )

        // 로그아웃 설정
        .logout(logout -> logout
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout.do")) // 로그아웃 URL 설정
            .logoutSuccessUrl("/") // 로그아웃 성공 시 리다이렉트
            .invalidateHttpSession(true) // 세션 무효화
            .deleteCookies("JSESSIONID") // 쿠키 삭제
            .permitAll()
        );

    return http.build();
  }
}
