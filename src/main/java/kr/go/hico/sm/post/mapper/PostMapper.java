package kr.go.hico.sm.post.mapper;

import java.util.List;
import kr.go.hico.sm.post.vo.InsertPostVo;
import kr.go.hico.sm.post.vo.SearchPostVo;
import kr.go.hico.sm.post.vo.PostVo;
import kr.go.hico.sm.post.vo.SavePostVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {

  List<PostVo> getPostListByBoardId(Long boardId);

  PostVo getPostById(SearchPostVo searchPostVo);

  int updatePost(SavePostVo savePostVo);

  int insertPost(InsertPostVo insertPostVo);
}
