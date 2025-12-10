package kr.go.config;

import kr.go.config.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 활성화
public class SecurityConfig {

  @Autowired // ContextConfig에서 정의한 빈을 주입받음
  private AccessDeniedHandler accessDeniedHandler;

  @Autowired // ContextConfig에서 정의한 빈을 주입받음
  private AuthenticationEntryPoint authenticationEntryPoint;

  @Bean // JWT 필터 빈 등록
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter();
  }

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
        .csrf(csrf -> csrf.disable()) // 람다식 최신 설정 방식

        // 💡 세션 사용 안 함 설정 (Stateless)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // 예외 처리 설정: 401 Unauthorized 및 403 Forbidden 시 커스텀 핸들러 사용
        .exceptionHandling(exception -> exception
            .authenticationEntryPoint(authenticationEntryPoint) // 인증 실패 시 401 처리
            .accessDeniedHandler(accessDeniedHandler)         // 권한 부족 시 403 처리
        )
        // URL 별 권한 설정
        .authorizeHttpRequests(authorize -> authorize
            // 로그인 API 엔드포인트는 인증 없이 접근 허용
            .requestMatchers(new AntPathRequestMatcher("/api/auth/login.do")).permitAll()
            // 메인화면..개발용
            .requestMatchers(new AntPathRequestMatcher("/home.do")).permitAll()
            // 정적 리소스 및 Swagger
            .requestMatchers(
                new AntPathRequestMatcher("/css/**"),
                new AntPathRequestMatcher("/js/**"),
                new AntPathRequestMatcher("/images/**"),
                new AntPathRequestMatcher("/swagger-ui.html"),
                new AntPathRequestMatcher("/swagger-ui/**"),
                new AntPathRequestMatcher("/v3/api-docs/**"),
                new AntPathRequestMatcher("/webjars/**"),
                new AntPathRequestMatcher("/swagger-resources/**"),
                new AntPathRequestMatcher("/v2/api-docs"),
                new AntPathRequestMatcher("/")
            ).permitAll()

            // 그 외 모든 요청은 인증된 사용자만 접근 가능
            .anyRequest().authenticated()
            //.anyRequest().denyAll()
        )

    /*
        폼 기반 로그인 설정
        .formLogin(formLogin -> formLogin
            .loginPage("/member/login") // 커스텀 로그인 페이지 URL 지정 (컨트롤러 매핑 필요)
            .loginProcessingUrl("/perform_login") // 실제 로그인 처리 요청 URL (시큐리티가 처리)
            .defaultSuccessUrl("/") // 로그인 성공 후 이동할 페이지
            .failureUrl("/member/login?error") // 로그인 실패 시 이동할 페이지
            .permitAll()
        )
    */
        // 로그아웃 설정
        .logout(logout -> logout
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 로그아웃 URL 설정
            .logoutSuccessUrl("/") // 로그아웃 성공 시 리다이렉트
            .invalidateHttpSession(true) // 세션 무효화
            .deleteCookies("JSESSIONID") // 쿠키 삭제
            .permitAll()
        );

    // 💡 UsernamePasswordAuthenticationFilter 이전에 JWT 필터를 추가
    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
