![1728717394611](https://github.com/user-attachments/assets/4e306f1f-fe81-445a-afae-329099641ebb)
# DewMinas
이력서 템플릿 공유 웹 사이트 듀미나스(DewMinas)

제공 기능 : 이력서 관리(등록, 수정, 삭제, 조회), 프로젝트 관리(등록, 수정, 삭제, 조회), 템플릿 생성 기능
캡스톤 디자인(2024-2)

server : springboot3, java, thymeleaf
client : js(vanilla), html, css

## 백엔드 애플리케이션 구조
![image](https://github.com/user-attachments/assets/ddaf8030-15ce-4f53-bca4-17a5f44e239c)


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
