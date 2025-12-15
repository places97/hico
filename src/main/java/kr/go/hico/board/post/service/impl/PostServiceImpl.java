package kr.go.hico.board.post.service.impl;

import java.util.List;
import kr.go.hico.board.post.mapper.PostMapper;
import kr.go.hico.board.post.service.PostService;
import kr.go.hico.board.post.vo.PostRequestVo;
import kr.go.hico.board.post.vo.PostVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

  @Autowired
  private PostMapper postMapper;

  @Override
  public List<PostVo> getPostListByBoardId(Long boardId) {

    List<PostVo> postVoList;
    postVoList = postMapper.getPostListByBoardId(boardId);

    return postVoList;
  }

  @Override
  public PostVo getPostById(PostRequestVo postRequestVo) {

    return postMapper.getPostById(postRequestVo);
  }
}
