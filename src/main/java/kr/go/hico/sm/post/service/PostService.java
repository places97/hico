package kr.go.hico.sm.post.service;

import kr.go.hico.sm.post.vo.InsertPostVo;
import kr.go.hico.sm.post.vo.SearchPostVo;
import kr.go.hico.sm.post.vo.PostVo;

import java.util.List;
import kr.go.hico.sm.post.vo.SavePostVo;

public interface PostService {

  List<PostVo> getPostListByBoardId(Long boardId);

  PostVo getPostById(SearchPostVo searchPostVo);

  boolean updatePost(SavePostVo savePostVo);

  boolean insertPost(InsertPostVo insertPostVo);

  boolean savePost(SavePostVo savePostVo);
}
