package kr.go.hico.sm.board.vo;

import kr.go.hico.ts.test.vo.PageVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardVO extends PageVO {

  private Long boardId;
  private Long authId;
  private String boardName;
  private String boardDescription;
  private String useYn;
  
  private SchVO SchVO;
  
  @Getter
  @Setter
  @ToString
  @NoArgsConstructor
  @AllArgsConstructor
  public class SchVO {

  	/** 검색 조건 (예: ID, Title 등) */
      private String schCondition;

      /** 검색 키워드 */
      private String schKeyword;

      /** 검색 시작일 */
      private String schStartDate;

      /** 검색 종료일 */
      private String schEndDate;
      
      /** 사용 여부 (Y/N) */
      private String useYn; 
      
      /** 삭제 여부 (Y/N) */
      private String delYn;
  	  
  }
}


