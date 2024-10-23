# github로 협업하기.🪛
### 1. 참여자 매뉴얼.
사전 확인사항 : git 설치 확인, git config --global user.name, git config --global user.email 수행 했는지 체크.
```
# git config --global user.name "본인이름"
# git config --global user.email "본인 이메일"
// 반드시 본인 깃허브 정보와 일치 시킬 것.
```
#### 환경 설정 절차.
1. 원본 리포지토리 방문하여 fork 누르기.
2. fork된 리포지토리로 이동하기.
3. url 복사하여 clone 수행하기.
4. 원본 리포지토리(** fork된 것 X임에 주의 **) url을 remote로 추가하기.
```
# git remote add real-dewminas 리포지토리url
# git remote -v
```

#### 협업 수행 하기.
1. 새로운 브랜치를 생성한다.
```
# git branch 새로운브랜치이름

// 생성된 브랜치 확인하기.
# git branch
```
2. 해당 브랜치로 이동한다.
```
# git checkout 새로운브랜치이름
```
3. 작업을 수행한다.
4. 변경된 내용을 커밋, 푸쉬한다.
```
# git add (변경된 내용) // 보통 git add . 을 사용해 모두 스테이징 한다.
# git commit -m "새롭게 변경된 작업 내용 설명"
# git push origin 새로운브랜치이름
```
***_origin임을 주의할 것. 환경설정 절차에서 git remote로 추가한 리포지토리가 아니다!_❌***
5. fork했던 github 페이지를 브라우저로 연다.
6. 활성화 되어 있는 pull request 버튼을 클릭한다.
7. 만약 conflics가 있다는 경고가 생기면, 해당 파일 내용들을 수정하여 conflic가 생기지 않도록 수정한 뒤 다시 시도한다.
