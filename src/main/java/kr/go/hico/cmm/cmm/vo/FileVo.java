package kr.go.hico.cmm.cmm.vo;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 파일
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FileVo {

  private Long fileId;         // 파일 ID (DB Primary Key)
  private String originalName; // 원본 파일명
  private String storedName;   // 저장된 파일명 (UUID 포함)
  private String filePath;     // 저장 경로
  private Long fileSize;       // 파일 크기 (바이트)
  private String contentType;  // 파일 MIME 타입
  private LocalDateTime regDate;      // 등록일
  private String fileTy;     // 구분?

}
