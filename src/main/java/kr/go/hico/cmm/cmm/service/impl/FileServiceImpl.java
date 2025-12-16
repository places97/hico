package kr.go.hico.cmm.cmm.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import kr.go.hico.cmm.cmm.service.FileService;
import kr.go.hico.cmm.cmm.vo.FileVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

  //@Autowired
  //private FileMapper fileMapper;

  @Value("${file.upload.dir}")
  private String fileDir;

  @Override
  //@Transactional
  public FileVo uploadAndSaveFile(MultipartFile file) throws IOException {
    if (file.isEmpty()) {
      return null;
    }

    // 1. 파일 시스템 저장 경로 확인 및 생성
    Path uploadPath = Paths.get(fileDir);
    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }

    // 2. 고유 파일명 생성 및 저장
    String originalFilename = file.getOriginalFilename();
    String storeFilename = UUID.randomUUID().toString() + "_" + originalFilename;
    String filePath = fileDir + File.separator + storeFilename;
    file.transferTo(new File(filePath));

    FileVo FileVo = new FileVo();
    FileVo.setOriginalName(originalFilename);
    FileVo.setStoredName(storeFilename);
    FileVo.setFilePath(filePath);
    FileVo.setFileSize(file.getSize());
    FileVo.setContentType(file.getContentType());

    /* TODO : 3. File DB 처리
    fileMapper.insertFile(FileVo);
    */

    return FileVo;
  }

  @Override
  //@Transactional
  public List<FileVo> uploadAndSaveFiles(List<MultipartFile> files) throws IOException {
    List<FileVo> savedFiles = new ArrayList<>();

    for (MultipartFile file : files) {
      if (!file.isEmpty()) {
        FileVo savedFileVo = uploadAndSaveFile(file);
        if (savedFileVo != null) {
          savedFiles.add(savedFileVo);
        }
      }
    }
    return savedFiles;
  }

  @Override
  public FileVo getFile(Long fileId) {
    return new FileVo();
    //return fileMapper.getFile(fileId);
  }
}
