package kr.go.hico.board.post.service;

import kr.go.hico.board.post.vo.InsertPostVo;
import kr.go.hico.board.post.vo.SearchPostVo;
import kr.go.hico.board.post.vo.PostVo;

import java.util.List;
import kr.go.hico.board.post.vo.SavePostVo;

public interface PostService {

  List<PostVo> getPostListByBoardId(Long boardId);

  PostVo getPostById(SearchPostVo searchPostVo);

  boolean updatePost(SavePostVo savePostVo);

  boolean insertPost(InsertPostVo insertPostVo);

  boolean savePost(SavePostVo savePostVo);
}
