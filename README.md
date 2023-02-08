<img src="https://user-images.githubusercontent.com/71685474/217293869-11c2946c-7cc5-4fe6-9779-8f22afff60c9.png" width="200" height="200"/>
</br>

# Udong 우동(우리들의 동아리)
- 동아리 운영을 위한 어플리케이션
- 교내 동아리 Solux 프로젝트 발표회에서 우수상 수상
<img src="https://user-images.githubusercontent.com/71685474/217294615-245b05f2-93d3-4343-b637-5a22087eaf49.png" width="500" height="300"/>
</br>

## 1. 제작 기간 & 참여 인원
- 2021년 6월 10일 ~ 8월 20일
- ios 1명, android 1명, android + backend 1명

</br>

## 2. 담당 역할
- android 파트 멘토
- android 파트에서 retrofit2를 활용해 서버와 통신하는 부분
- ERD 설계
- Backend 모든 부분

</br>

## 3. 사용 기술

#### `Backend`
  - Spring Boot 2.1.7
  - Java
  - Spring Data JPA
  - Gradle
  - Spring Security
  - Maria DB
#### `Android`
  - Android Studio
  - java
  - retrofit2
  - -gson
#### `IOS`
  - swift

</br>

## 4. ERD 설계
![image](https://user-images.githubusercontent.com/71685474/217288194-903eb8d5-d385-4d72-b998-c047727200a0.png)

</br>

## 5. 기능
1. 구글 로그인, 동아리 개설 및 가입하기
- 동아리 회장이 동아리 코드를 생성하고, 회원들은 그 코드를 통해 동아리를 조회하여 가입(가입 시에 동아리 직급을 설정하여 각 게시판 접근 권한을 다르게 갖도록 함)
</br>

<img src="https://user-images.githubusercontent.com/71685474/217546998-673c116a-104a-4f40-b65d-2a40c3cda70f.png" width="250" height="410"/><img src="https://user-images.githubusercontent.com/71685474/217547049-ff249854-6b5b-4291-84e3-69e2dff902f4.png" width="250" height="410"/><img src="https://user-images.githubusercontent.com/71685474/217295207-5ab1edf2-6997-466a-9f94-c3d4ed7446ed.gif" width="250" height="410"/>

2. 공지 게시판
- 동아리 운영진만 글을 작성 할 수 있는 게시판
- 로그인 되어있는 사용자가 공지 작성 권한이 있는지 확인한 후 있을 경우에만 글 작성 버튼이 보이도록 구현함.
</br>

<img src="https://user-images.githubusercontent.com/71685474/217552673-9950df4b-b1c4-491c-8075-017e185d0845.png" width="200" height="410"/><img src="https://user-images.githubusercontent.com/71685474/217552788-ed42a19d-b624-4b76-8cd2-7e54e639c36a.png" width="210" height="410"/><img src="https://user-images.githubusercontent.com/71685474/217552257-207f6ae0-0fa6-418a-b60d-97f801c80b46.gif" width="250" height="410"/>


3. 회비 게시판
- 회장과 총무만 글 작성이 가능한 게시판
- 날짜별로 벌금 등의 수입과 회식 등에 사용된 회비의 지출 내역을 한 눈에 볼 수 있고, 해당 날짜 클릭 시 상세 내용 확인 가능
</br>

<img src="https://user-images.githubusercontent.com/71685474/217553998-c74bf67b-d5ef-43ff-b7fb-c621a0f210b1.png" width="210" height="410"/><img src="https://user-images.githubusercontent.com/71685474/217554113-9cbd7cdd-5f43-4307-8ff2-b49cc2da5e9f.png" width="210" height="410"/><img src="https://user-images.githubusercontent.com/71685474/217551080-9722241f-dfaa-4ba6-94a1-ae3823d1f474.gif" width="250" height="410"/>

4. 조별 게시판
- 각 조별로 각 주차 회의 내용을 작성할 수 있는 게시판
- 회장이 조를 만들고, 조원들을 배정한 후 사용 가능
- 각 조의 조원들만 자신의 조 게시판에 글 작성 가능
</br>

<img src="https://user-images.githubusercontent.com/71685474/217559666-0d3bb9e0-bdbf-467c-8dce-e6d13349941c.gif" width="220" height="410"/><img src="https://user-images.githubusercontent.com/71685474/217559839-7fac2676-9569-48da-a80e-f4fb2bcb4baa.gif" width="220" height="410"/>

