package kr.go.ctl.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.HashMap;
import java.util.Map;
import kr.go.util.JwtTokenProvider;
import kr.go.vo.cmm.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Api(tags = "인증 및 토큰 발급 API", description = "사용자 로그인 및 JWT 토큰 관리")
public class AuthRestCtl {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  JwtTokenProvider tokenProvider;

  @PostMapping("/login.do")
  @ApiOperation(value = "사용자 로그인 및 JWT 토큰 발급", notes = "아이디와 비밀번호를 사용하여 인증하고 Access Token을 발급받습니다.")
  public ResponseEntity<ResultVo> authenticateUser(
      @ApiParam(value = "사용자 아이디", required = true, example = "testuser")
      @RequestParam String username,
      @ApiParam(value = "비밀번호", required = true, example = "1234")
      @RequestParam String password) {

    // 1. 사용자 인증 시도 (DB 조회 및 비밀번호 검증 포함)
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            username,
            password
        )
    );

    // 2. 인증 성공 후 SecurityContext에 저장
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // 3. JWT 토큰 생성
    String jwt = tokenProvider.generateToken(authentication);

    // 4. 클라이언트에게 반환할 데이터 (토큰 정보를 Map에 담습니다)
    Map<String, String> tokenResponseData = new HashMap<>();
    tokenResponseData.put("accessToken", jwt);
    tokenResponseData.put("tokenType", "Bearer");

    // 5. ResultVo.success() 메소드를 사용하여 표준 응답 형식 변환
    ResultVo resultVo = ResultVo.success(tokenResponseData);

    return ResponseEntity.ok(resultVo);
  }
}