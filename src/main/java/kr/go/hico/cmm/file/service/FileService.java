package kr.go.hico.cmm.file.service;

import java.io.IOException;
import java.util.List;
import kr.go.hico.cmm.file.vo.FileVo;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
  /**
   * 단일 파일을 저장하고 DB에 정보를 기록합니다.
   * @param file 업로드된 파일 객체
   * @return 저장된 파일 정보 VO
   * @throws IOException 파일 시스템 처리 중 발생하는 I/O 예외
   */
  FileVo uploadAndSaveFile(MultipartFile file) throws IOException;

  /**
   * 여러 파일을 저장하고 각 파일 정보를 DB에 기록합니다.
   * 각 파일 처리는 단일 트랜잭션으로 관리됩니다.
   *
   * @param files 업로드된 MultipartFile 객체 리스트
   * @return 저장된 파일 정보 VO 리스트
   * @throws IOException 파일 시스템 처리 중 발생하는 I/O 예외
   */
  List<FileVo> uploadAndSaveFiles(List<MultipartFile> files) throws IOException;

  /**
   * DB ID로 파일 정보를 조회합니다.
   * @param fileId 파일 ID
   * @return 파일 정보 VO
   */
  FileVo getFile(Long fileId);
}
