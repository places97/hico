package kr.go.common.security.vo;

import java.util.Collection;
import kr.go.hico.sm.user.vo.UserVo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class PrincipalDetails implements UserDetails {

  private static final long serialVersionUID = -4125669445850507235L;

  private final UserVo userVo;
  private final Collection<? extends GrantedAuthority> authorities;

  public PrincipalDetails(UserVo userVo, Collection<? extends GrantedAuthority> authorities) {
    this.userVo = userVo;
    this.authorities = authorities;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
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
}