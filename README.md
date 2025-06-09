![1728717394611](https://github.com/user-attachments/assets/4e306f1f-fe81-445a-afae-329099641ebb)
# DewMinas
이력서 템플릿 공유 웹 사이트 듀미나스(DewMinas)

제공 기능 : 이력서 관리(등록, 수정, 삭제, 조회), 프로젝트 관리(등록, 수정, 삭제, 조회), 템플릿 생성 기능
캡스톤 디자인(2024-2)

server : springboot3, java, thymeleaf
client : js(vanilla), html, css

## 백엔드 애플리케이션 구조
![image](https://github.com/user-attachments/assets/ddaf8030-15ce-4f53-bca4-17a5f44e239c)

## 발생했던 문제.
이력서 수정 API의 수행성능이 매우 느렸음.
SQL 실행 로그를 살펴본 결과 : delete 대상들을 하나하나 Select하는 방식으로 Data jpa가 실행되고 있었음. delete의 rollback을 위한 백업 과정이 오버헤드를 발생시켰다.

채택한 해결 방안 : 꼭 필요한 순간이 아니라면 DB 접근이 없도록 하자. select 만으로 접근하여 동기화로 인한 지연을 최소화하자.


## 데이터 수정 여부 확인(Contrastable), 엔티티 수정 연산 추상화(Updatable), DTO와 DAO 변환 추상화(Specifiable)
![image](https://github.com/user-attachments/assets/6cda7c49-128c-4a23-ac55-f68551d50682)

### 코드 구현.
1. 요청으로 전송된 이력서 항목 인스턴스들이 PK 값을 가지고 있다면 변경 확인을 위해 DB에서 읽어옴.
2. 위 과정에서 메서드 인자도 전달된 getAllIds를 사용해 JpaRepository를 구체적인 리포지토리 타입으로 캐스팅하고 기본키를 읽어올 수 있음.
3. 각 엔티티들에 대해 필요한 연산을 확인.  
   3.1. 기본키가 있던 DTO라면 데이터 변경을 확인함.  
   3.2. 기본키가 없는 DTO라면 새로운 데이터이므로 삽입함.  
4. 위 과정을 거치고 남아있는 ID들은 제거 대상이므로 delete를 수행함.
   
![image](https://github.com/user-attachments/assets/d1b589c6-b404-42b5-b659-0eb8392a2733)

### 부하 테스트
테스트 시나리오.
1. 엔티티 종류별로 1000건의 데이터 삽입
2. 삽입된 모든 데이터를 읽어옴.
3. 랜덤하게 100개 씩 데이터를 수정하고 총 100번 수정 API를 호출함.
4. 총 소요시간을 a. 전체삭제, 다시 삽입 방식과 b. 기본키를 사용한 검토 방식으로 구분하여 수행함.

![image](https://github.com/user-attachments/assets/017c2799-6319-4109-a723-018d412ce049)

테스트 결과. (https://velog.io/@cosmos334/SpringBoot-%EC%9D%B4%EB%A0%A5%EC%84%9C-%EC%88%98%EC%A0%95-API-%EA%B0%9C%EC%84%A0-%ED%85%8C%EC%8A%A4%ED%8A%B8)
![image](https://github.com/user-attachments/assets/72581266-3479-410a-92d6-eda424814daa)
