package kr.go.hico.board.post.mapper;

import java.util.List;
import kr.go.hico.board.post.vo.InsertPostVo;
import kr.go.hico.board.post.vo.SearchPostVo;
import kr.go.hico.board.post.vo.PostVo;
import kr.go.hico.board.post.vo.SavePostVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {

  List<PostVo> getPostListByBoardId(Long boardId);

  PostVo getPostById(SearchPostVo searchPostVo);

  int updatePost(SavePostVo savePostVo);

  int insertPost(InsertPostVo insertPostVo);
}
