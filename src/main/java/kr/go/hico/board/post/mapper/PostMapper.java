package kr.go.hico.board.post.mapper;

import java.util.List;
import kr.go.hico.board.post.vo.PostRequestVo;
import kr.go.hico.board.post.vo.PostVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {

  List<PostVo> getPostListByBoardId(Long boardId);

  PostVo getPostById(PostRequestVo postRequestVo);
}
