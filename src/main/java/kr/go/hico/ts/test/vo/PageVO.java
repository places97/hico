package kr.go.hico.ts.test.vo;

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
public class PageVO {

    private int page = 1;        // 현재 페이지
    private int pageSize = 10;   // 한 페이지당 건수
    private int totalCount;      // 전체 건수

    /* DB 조회용 */
    public int getOffset() {
        return (page - 1) * pageSize;
    }

    /* 화면용 */
    public int getTotalPage() {
        return (int) Math.ceil((double) totalCount / pageSize);
    }
    
    // 페이징 그룹 관련
    private int groupSize = 10; // 한 화면에 표시할 페이지 수

    // 시작 페이지
    public int getStartPage() {
        return ((page - 1) / groupSize) * groupSize + 1;
    }

    // 끝 페이지
    public int getEndPage() {
        return Math.min(getStartPage() + groupSize - 1, getTotalPage());
    }

    // 이전 페이지 그룹
    public Integer getPrevPage() {
        return getStartPage() > 1 ? getStartPage() - 1 : null;
    }

    // 다음 페이지 그룹
    public Integer getNextPage() {
        return getEndPage() < getTotalPage() ? getEndPage() + 1 : null;
    }

    // getter / setter 생략
}