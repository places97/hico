package kr.go.hico.sm.post.service.impl;

import java.time.LocalDate;
import java.util.List;
import javax.transaction.Transactional;
import kr.go.hico.sm.post.mapper.PostMapper;
import kr.go.hico.sm.post.service.PostService;
import kr.go.hico.sm.post.vo.InsertPostVo;
import kr.go.hico.sm.post.vo.SearchPostVo;
import kr.go.hico.sm.post.vo.PostVo;
import kr.go.hico.sm.post.vo.SavePostVo;
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
  public PostVo getPostById(SearchPostVo searchPostVo) {

    return postMapper.getPostById(searchPostVo);
  }

  @Override
  public boolean updatePost(SavePostVo savePostVo) {
    int updateCount = postMapper.updatePost(savePostVo);

    return updateCount > 0;
  }

  @Override
  public boolean insertPost(InsertPostVo insertPostVo) {
    //insertPostVo.setCreatedAt(LocalDate.now());
    //insertPostVo.setUpdatedAt(LocalDate.now());
    insertPostVo.setUserId(1L);

    int insertCount = postMapper.insertPost(insertPostVo);

    return insertCount > 0;
  }

  @Override
  @Transactional
  public boolean savePost(SavePostVo savePostVo) {
    // 1.
    if (savePostVo.getPostId() == null && savePostVo.getBoardId() != null) {
      InsertPostVo insertPostVo = InsertPostVo.builder()
          .boardId(savePostVo.getBoardId())
          .title(savePostVo.getTitle())
          .content(savePostVo.getContent())
          .delYn("N")
          //.createdAt(LocalDate.now())
          //.updatedAt(LocalDate.now())
          .userId(1L)
          .build();
      int insertCount = postMapper.insertPost(insertPostVo);

      return insertCount > 0;
    } else if (savePostVo.getPostId() != null && savePostVo.getBoardId() != null) {
      SearchPostVo searchVo = SearchPostVo.builder()
          .postId(savePostVo.getPostId())
          .boardId(savePostVo.getBoardId())
          .build();

      if (getPostById(searchVo) != null) {
        int updateCount = postMapper.updatePost(savePostVo);
        return updateCount > 0;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }
}
