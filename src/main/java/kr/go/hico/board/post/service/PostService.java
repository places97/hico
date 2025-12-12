package kr.go.hico.board.post.service;

import kr.go.hico.board.post.vo.PostVo;

import java.util.List;

public interface PostService {

  List<PostVo> getPostListByBoardId(Long boardId);

  PostVo getPostById(Long postId);
}
