package kr.go.hico.cmm.file.mapper;

import kr.go.hico.cmm.file.vo.FileVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
  void insertFile(FileVo fileVO);
  FileVo getFile(Long fileId);
}

