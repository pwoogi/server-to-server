# server-to-server
## Rest Template, Naver API 연동 복습

1. 서버 두대 켜놓고 RestTemplate 사용하여 통신 학습
2. RestTemplate 심화 공부

<br />
* 이 파트의 공부가 굉장히어려웠음
    1. header는 고정된 값을 받아야하고 body에만 클라이언트 요청에 의해 변경될 때 지네릭타입을 이용하는 것
    2. 전달할 dto 데이터를 RequestEntity로 header의 타입을 설정하고 ResponseEntity를 사용해서 보내주는 방식 
