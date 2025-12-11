package kr.go.hico.cmm.security.svc;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("principalSvc")
public class PrincipalSvc implements UserDetailsService {

  /**
    * 사용자가 입력한 username(ID)을 기반으로 DB에서 사용자 상세 정보를 조회합니다.
    * AuthenticationProvider에 의해 자동으로 호출됩니다.
    *
    * @param username 로그인 시 사용자가 입력한 ID
    * @return UserDetails 스프링 시큐리티에서 사용하는 사용자 정보 객체
    * @throws UsernameNotFoundException 사용자를 찾지 못했을 때 발생
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    // TODO : DB 사용자정보 조회 로직 구현 필요
    // UserVO userVO = userMapper.selUser(name);

    // 테스트용 임시 반환 (비밀번호: 1234)
    if (!"testuser".equals(username)) {
      throw new UsernameNotFoundException("해당 사용자 ID를 찾을 수 없습니다: " + username);
    }

    //임시
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String encodedPassword = encoder.encode("1234");

    //final String ENCODED_1234 = "$2a$10$T1q/c2k4q1H.8Qv9z/7tQ.M.z.r.1fQ.q.q.q.q.q.q.q.q.q.q";

    return org.springframework.security.core.userdetails.User.builder()
        .username("testuser")
        .password(encodedPassword)
        .roles("USER", "ADMIN")
        .build();
  }
}
