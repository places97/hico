package kr.go.hico.cmm.cmm.mapper;

import kr.go.hico.cmm.cmm.vo.FileVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
  void insertFile(FileVo fileVO);
  FileVo getFile(Long fileId);
}

