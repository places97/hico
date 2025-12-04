package kr.go.exception;

import kr.go.enums.ResCd;
import lombok.Getter;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class CmmException extends ResponseStatusException  {

  private final ResCd resCd;

  public CmmException(ResCd resCd) {
    super(resCd.getHttpStatus(), resCd.getMsg());
    this.resCd = resCd;
  }

  public CmmException(ResCd resCd, String message) {
    super(resCd.getHttpStatus(), resCd.getMsg() + ":" + message);
    this.resCd = resCd;
  }

  public CmmException(ResCd resCd, String message, Throwable cause) {
    super(resCd.getHttpStatus(), resCd.getMsg() + ":" + message, cause);
    this.resCd = resCd;
  }

}
