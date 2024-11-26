📷REPHOTO
-----------
<img src="https://github.com/user-attachments/assets/c7594e23-d403-403c-91bb-cb158d1980b7" alt="REPHOTO-logo-long" width="50%"/>

### 사진 리뷰 사이트 
- 사용자들이 자신이 촬영한 사진을 자유롭게 업로드하고, 다른 사용자로부터 피드백과 리뷰를 받을 수 있는 플랫폼입니다.  
- 개인의 프로필 페이지에서 해당 사용자가 올린 사진을 모아볼 수 있습니다.  
- 사용자들이 올린 사진을 제목, 작성자, 내용을 토대로 검색할 수 있습니다.  
- 원하는 사진을 다운로드 받을 수 있습니다.  

<br/>

🌱팀원구성
-----------
김민경  
이준영  
<br/>

🖥️개발환경
-----------
![제목을-입력해주세요_-008](https://github.com/user-attachments/assets/e62a5cde-e0f2-48c6-b94d-9e2ed3482414)


📂프로젝트 구조
-----------
```
C:.
├─backend
│  ├─.mvn                  # Maven Wrapper 관련 설정
│  │  └─wrapper
│  ├─src                   # 실제 프로젝트 소스 코드
│  │  ├─main
│  │  │  ├─java
│  │  │  │  └─com
│  │  │  │      └─ssafy
│  │  │  │          └─mvc
│  │  │  │              ├─config
│  │  │  │              ├─controller
│  │  │  │              ├─interceptor
│  │  │  │              ├─model
│  │  │  │              │  ├─dao
│  │  │  │              │  ├─dto
│  │  │  │              │  └─service
│  │  │  │              └─view
│  │  │  └─resources
│  │  │      ├─mappers
│  │  │      └─static
│  │  │          └─img    # 정적 이미지 리소스 
│  │  └─test              # 테스트 코드
│  │      └─java
│  │          └─com
│  │              └─ssafy
│  │                  └─mvc
│  └─pom.xml              # Maven 빌드 설정 파일
├─frontend
│  └─rephoto
│      ├─public          
│      ├─src              # Vue 소스 코드
│      │  ├─assets        # CSS, JS, 이미지 등 정적 자원
│      │  ├─components    # Vue 컴포넌트
│      │  ├─router        # Vue 라우터 설정
│      │  ├─stores        # Pinia 관련 파일
│      │  └─views         # Vue 뷰 페이지
│      ├─package.json     # Node.js 의존성 정보
│      ├─package-lock.json # Node.js 의존성 버전 고정 
│      ├─vite.config.js   # Vite 설정 파일
└─.gitignore              

```
<br/>

📌ERD
-----------
![REPHOTO_ERD](https://github.com/user-attachments/assets/cfc7b552-ed88-4d86-a0a2-40a8d271ac68)


📌Usecase Diagram
-----------
![REPHOTOUseCaseDiagram](https://github.com/user-attachments/assets/fbf9768e-f5fb-4f90-aeb9-1312bcdbd572)

📌Class Diagram
-----------
![REPHOTOClassDiagram](https://github.com/user-attachments/assets/15635b65-88f8-4ce8-91c6-24c9ab953bf2)


👯역할분담
-----------
![제목을-입력해주세요_-009](https://github.com/user-attachments/assets/cc372d77-a03d-4571-b9b0-5e52be881691)

<br/>

🚀개발 기간 및 작업 관리
-----------
### 개발 기간
- 2024.11.14 - 2024.11.26
![스크린샷 2024-11-26 210257](https://github.com/user-attachments/assets/27fdded3-e541-4616-b3c0-bf0d2ee34157)

### 작업 관리
- Notion과 GitHub를 이용한 협업 진행
- 컨벤션, 명세서, 트러블 슈팅, 문서 등은 Notion으로 관리
- 소스코드는 GitHub로 관리

<br/>

✨구현 화면
-----------
![localhost_5173_](https://github.com/user-attachments/assets/4d1eb897-44ca-4362-a2d6-6d4785304d46)
![localhost_5173_ (2)](https://github.com/user-attachments/assets/b017ae29-9aa6-4720-bd65-5dc844f767f8)
![localhost_5173_user_regist](https://github.com/user-attachments/assets/c2e0b51e-07ad-4d5d-872e-1b48afc96de6)
![localhost_5173_user_regist (1)](https://github.com/user-attachments/assets/0432b839-cd75-497f-b406-71eeed5d7c9d)
![localhost_5173_ (3)](https://github.com/user-attachments/assets/a368d40c-392c-4d80-bab4-3653076f70f8)
![localhost_5173_board_1 (3)](https://github.com/user-attachments/assets/21664fc8-8595-4134-8923-6bde91338e73)
![localhost_5173_board_1 (5)](https://github.com/user-attachments/assets/132e1869-7f9f-4631-948e-2c89169383be)
![localhost_5173_ (6)](https://github.com/user-attachments/assets/3f317d5f-b6e1-4678-b979-ae812ef7bf2d)
![localhost_5173_ (7)](https://github.com/user-attachments/assets/80640878-5d52-435b-9ddd-7f579836c4ca)
![localhost_5173_board_1](https://github.com/user-attachments/assets/00d165b0-6a03-4c7b-9364-e11228b047f3)
![localhost_5173_board_1 (4)](https://github.com/user-attachments/assets/fafae9d7-a4b4-4c9f-ab66-45f2e871a2d3)
![localhost_5173_board_1 (2)](https://github.com/user-attachments/assets/2651733a-20cf-4bdd-b97c-5a7415c93ee9)

<br/>

❗신경 쓴 부분
-----------
### 접근 제한 설정
#### 프론트엔드
- NavigationGuard를 이용하여 로그인 여부에 따라 특정 라우터에 접근할 수 없게 함
- 로그인이 되어 있는 경우 로그인, 회원가입 화면에 접근 불가  
- 로그인이 되어 있지 않은 경우 시작 화면, 로그인 화면, 회원 가입 화면 이외에 접근 불가
- 글, 댓글 작성시 DB의 제약조건에 위배되는 경우를 watch로 감시하고 위배될 경우 해당 작업을 수행할 수 없도록 disabled 설정

#### 백엔드
- session에 로그인 아이디와 닉네임을 저장한 후 로그인 아이디의 여부에 따라 특정 로직을 실행할 수 없게 인터셉터를 설정하였다.
- 로그인이 되어 있는 경우 로그인 요청, 회원가입 요청을 차단함.
- 로그인이 되어 있지 않은 경우 로그인 요청, 회원 가입 요청, 중복 확인 여부를 제외한 모든 요청을 차단함.

<br/>

💥트러블 슈팅
-----------  
- [CORS 문제](https://puffy-echinodon-d88.notion.site/session-13a79b2ed9584afb889be7afc86b1dd4)
- [잘못된 이름으로 파일 업로드 및 게시글 작성 요청이 들어왔을 때 boardId 증가](https://puffy-echinodon-d88.notion.site/boardId-1453eaa46d158046b35bff44727f0f8a)

<br/>


⚙️설치 및 실행 방법
-----------
- 원활한 사용을 위해 크롬 웹 스토어에서 Vue.js devtools 다운
- node.js 사이트 (https://nodejs.org/en) 에서 다운
```
cd frontend/rephoto
npm i axios bootstrap bootstrap-icons
npm run dev
```

<br/>


🎯개선 목표
-----------
![제목을-입력해주세요_-022](https://github.com/user-attachments/assets/96bb33dc-c919-4b6b-b795-d4736ec177bd)

<br/>

😄프로젝트 후기
-----------
![REPHOTO-023](https://github.com/user-attachments/assets/2e64b898-b778-4b3d-ace2-b00da08056e4)


