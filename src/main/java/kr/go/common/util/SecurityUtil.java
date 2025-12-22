package kr.go.common.util;

import kr.go.common.security.vo.PrincipalDetails;
import kr.go.hico.sm.user.vo.UserVo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

  /**
   * 현재 로그인된 사용자의 정보를 UserVo 반환
   */
  public static UserVo getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return null;
    }

    Object principal = authentication.getPrincipal();

    if (principal instanceof PrincipalDetails) {
      PrincipalDetails userDetails = (PrincipalDetails) principal;
      return userDetails.getUserVo(); // 💡 UserVo 객체 반환
    } else {
      return null;
    }
  }

  /**
   * 현재 로그인된 사용자의 ID
   */
  public static Long getId() {
    UserVo userVo = getCurrentUser();
    return (userVo != null) ? userVo.getId() : null;
  }
}