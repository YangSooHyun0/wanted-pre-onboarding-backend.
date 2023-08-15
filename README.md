## 🖥️ 원티드 프리 온보딩 
### 개발자 성명 : 양수현 


## 🛠️ 프로젝트에 사용된 기술스택
<img src="https://img.shields.io/badge/IntelliJ IDEA-000000?style=flat-square&logo=IntelliJ IDEA&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=flat-square&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=flat-square&logo=springsecurity&logoColor=white"> <img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=flat-square&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/H2-41454A?style=flat-square&logo=&logoColor=white"> <img src="https://img.shields.io/badge/Java 11-FF160B?style=flat-square&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=gradle&logoColor=white"> <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white">
<br><br>

## 📄 API 명세
## 1. 사용자 회원가입
### Request

`POST http://localhost:8080/api/user/signup`

```
{
    "email": "test@test.com",
    "password": "12345678"
}
```
### Response

```
{
    "status": 200,
    "success": true,
    "response": null,
    "error": null
}
```
<br>

## 2. 사용자 로그인
### Request

### Request

`POST http://localhost:8080/api/user/signin`

```
{
    "email": "test@test.com",
    "password": "12345678"
}
```
### Response

```
Authorization: Bearer [JWT Token]
```
<br>

## 3. 새로운 게시글 생성
### Request

`POST http://localhost:8080/api/post/create`

```
{
  "title": "새로운 게시글 제목",
  "content": "새로운 게시글 내용"
}
```

### Response

```
{
  "id": 1,
  "user": {
    "id": 1,
    "email": "test@test.com",
    "password": "[hashed_password]",
    "role": "user"
  },
  "title": "새로운 게시글 제목",
  "content": "새로운 게시글 내용"
}
```
<br>

## 4. 게시글 목록 조회
### Request

`GET http://localhost:8080/api/post/list`

### Response

```
{
    "content": [
        // 생성한 게시글 목록
    ],
    "pageable": {
        "sort": {
            "unsorted": true,
            "sorted": false,
            "empty": true
        },
        "pageNumber": 0,
        "pageSize": 20,
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "totalElements": 1,
    "totalPages": 1,
    "last": true,
    "numberOfElements": 1,
    "sort": {
        "unsorted": true,
        "sorted": false,
        "empty": true
    },
    "number": 0,
    "first": true,
    "size": 20,
    "empty": false
}
```
<br>

## 5. 특정 게시글 조회
### Request

`GET http://localhost:8080/api/post/{id}`

### Response

```
상태 코드 200 OK
```
<br>

## 6. 특정 게시글 수정 
### Request

`PUT http://localhost:8080/api/post/{id}`

```
{
  "title": "수정된 게시글 제목",
  "content": "수정된 게시글 내용"
}
```

### Response

```
상태 코드 204 No Content
```
<br>

## 7. 특정 게시글 삭제
### Request

`DELETE http://localhost:8080/api/post/{id}`

```
Authorization: Bearer [JWT Token]
```

### Response

```
상태 코드 204 No Content
```
<br>
