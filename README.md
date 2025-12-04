# READ.MD

----
Tomcat Run Edit 설정
### Intellij 설정 : 
1. Run -> Edit Configurations.. 
2. Add Server -> Tomcat 8.5.99
3. VM Option : -Dfile.encoding=UTF-8 -Dspring.profiles.active=local
4. Deployment 이동 -> add Artifact -> war exploded -> Application context : /


### eclipse 설정 :
1. .setting/org.eclipse.wst.common.project.facet.core 파일의 

----

### 개발 가이드

* **메소드 명명 규칙**
  1. 등록(ins***) ex) insUser
  2. 수정(upt***) ex) uptUser
  3. 단건조회(get***) ex) getUser
  4. 목록조회(sel***) ex) selUser

+ **메소드 정렬**
  1. 목록 조회, 단건조회, 등록, 수정 순으로 정렬한다
  2. 내부 메소드는 최하단에 정렬한다.
  3. 재사용가능한 Utils는 모듈화하여 Util 패키지에 생성


+ **예외 처리**
  1. 비즈니스 로직 내 예외처리는 CmmExcepton 사용을 지향
  2. 대분류별 예외처리 코드는 ResCd에 +1 하여 생성 후 사용
  3. ResCd는 업무코드 별 에러코드 생성(ex: 학습공동체 생성에러 : LC_INS)


+ **코드 정렬**
  1. File -> Settings ->Editor -> CodeStyle -> Google Style적용
  2. 쿼리 (xml)은 좌측정렬을 기본으로 한다(Ctrl + alt + L)
  3. 새로운 스코프가 시작될 때는 탭으로 1단계 들여쓰기


+ **공통코드**
  1. 시스템 내부의 고정된 상수나 타입 안전성이 필요한 불변 데이터는 Enum
  2. 운영 중 변경 가능성이 있는 설정 정보나 유연성이 필요한 가변 데이터는 공통 코드 테이블을 활용


+ **Annotation**
  1. AOP사용시 특성서비스 선언(x) Annotation 생성하여 사용하고 서비스 Layer에 선언
  2. Lombok 사용시 불필요 Annotation 선언 (x)
  3. Transaction은 서비스 Layer에 선언
---
### 
+ ****
