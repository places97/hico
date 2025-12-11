package kr.go.hico.cmm.security.vo;

import java.util.ArrayList;
import java.util.Collection;
import kr.go.hico.sm.user.vo.UserVo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class PrincipalDetails implements UserDetails {

  private static final long serialVersionUID = -4125669445850507235L;

  private UserVo userVo;

  public PrincipalDetails(UserVo userVo) { // 💡 생성자 파라미터 변경
    this.userVo = userVo;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> collect = new ArrayList<>();
    collect.add(new GrantedAuthority() {
      private static final long serialVersionUID = 174249776676957059L;

      @Override
      public String getAuthority() {
        // return userVo.getRoles(); // 권한처리
        return "ROLE_USER";
      }
    });
    return collect;
  }

  @Override
  public String getPassword() {
    return userVo.getPassword();
  }

  @Override
  public String getUsername() {
    return userVo.getName();
  }

  @Override
  // 💡 계정이 만료되지 않았음을 알림
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  // 💡 계정이 잠겨있지 않음을 알림
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  // 💡 비밀번호가 만료되지 않았음을 알림
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  // 💡 계정이 활성화되었음을 알림
  public boolean isEnabled() {
    return true;
  }

  public UserVo getUserVo() {
    return userVo;
  }

  public void setUserVo(UserVo userVo) {
    this.userVo = userVo;
  }
}
