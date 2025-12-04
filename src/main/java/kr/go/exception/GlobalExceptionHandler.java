package kr.go.exception;

import java.io.IOException;
import kr.go.enums.ResCd;
import kr.go.vo.cmm.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {
  /**
   * Application Custom Exception
   * @param ex
   * @return
   */
  @ExceptionHandler(CmmException.class)
  protected ResponseEntity<ResultVo> handleAppException(CmmException ex) {
    log.error("에러발생 코드: {}, 메시지: {}", ex.getResCd().getCode(), ex.getMessage());
    return new ResponseEntity<>(ResultVo.fail(ex.getResCd()), ex.getResCd().getHttpStatus());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResultVo> handleException(Exception ex) {
    log.error("에러발생  Exception");
    ResultVo resultVo = new ResultVo();
    resultVo.setData(null);
    resultVo.setResCd("999");
    resultVo.setResMsg(ex.getMessage());
    return new ResponseEntity<>(resultVo, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(IOException.class)
  public ResponseEntity<ResultVo> handleIOException(IOException ex) {
    // 1. Stream closed 예외 분리 (클라이언트 연결 종료로 간주)
    if (ex.getMessage().contains("Stream closed")) {
      log.debug("클라이언트 연결 조기 종료 감지: {}", ex.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 상태 코드 500만 반환
    }

    // 2. 그 외의 IOException 처리 (파일, 소켓 등 다른 심각한 문제)
    log.error("심각한 I/O 예외 발생", ex);
    CmmException exception = new CmmException(ResCd.CM_IO);
    return new ResponseEntity<>(ResultVo.fail(exception.getResCd()), exception.getResCd().getHttpStatus());
  }
}
