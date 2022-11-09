package com.example.client.service;

import com.example.client.dto.Req;
import com.example.client.dto.UserRequest;
import com.example.client.dto.UserResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class RestTemplateService {

    public UserResponse hello(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/hello")
                .queryParam("name", "aaa") //queryParam으로 값을 주면 주소가 바뀜
                .queryParam("age", 99)
                .encode()
                .build()
                .toUri();
        System.out.println(uri.toString());

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<UserResponse> result = restTemplate.getForEntity(uri, UserResponse.class);
        System.out.println(result.getStatusCode());
        System.out.println(result.getBody());


        return result.getBody();
    }

    public void post(){

        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/user/{userId}/name/{userName}")
                .encode()
                .build()
                .expand(100, "chris") // pathVariable 해당 변수를 이어서 작성해주면 됨.
                .toUri();
        System.out.println(uri);

        UserRequest req = new UserRequest();
        req.setName("chris");
        req.setAge(10);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> res = restTemplate.postForEntity(uri, req, String.class);

        System.out.println(res.getStatusCode());
        System.out.println(res.getHeaders());
        System.out.println(res.getBody());

//        return res.getBody(); 만약 서버가 내려줄 형식을 모르는 경우 void를 활용해서 값을 찍어볼 수 있음.

    }

    public UserResponse exchange(){

        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/user/{userId}/name/{userName}")
                .encode()
                .build()
                .expand(100, "chris") // pathVariable 해당 변수를 이어서 작성해주면 됨.
                .toUri();
        System.out.println(uri);

        UserRequest req = new UserRequest();
        req.setName("chris");
        req.setAge(10);

        RequestEntity<UserRequest> requestEntity = RequestEntity
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-authorization", "abcd")
                .header("custom-header", "ffff")
                .body(req);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> responseEntity = restTemplate.exchange(requestEntity, UserResponse.class);
        return responseEntity.getBody();
    }

    public Req<UserResponse> genericExchange(){

        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/user/{userId}/name/{userName}")
                .encode()
                .build()
                .expand(100, "chris") // pathVariable 해당 변수를 이어서 작성해주면 됨.
                .toUri();
        System.out.println(uri);

        UserRequest userRequest = new UserRequest();
        userRequest.setName("chris");
        userRequest.setAge(10);

        Req<UserRequest> req = new Req<>();

        req.setHeader(
            new Req.Header()
        );

        req.setResBody(
            userRequest
        );


        RequestEntity<Req<UserRequest>> requestEntity = RequestEntity
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-authorization", "abcd")
                .header("custom-header", "ffff")
                .body(req);

        RestTemplate restTemplate = new RestTemplate();
        

        ResponseEntity<Req<UserResponse>> response
                // ParameterizedTypeReference 클래스 사용할 때 자바 11버전에서 아직까지 에러가 속출해서 꼭 <>제너릭 타입 명시해야함.
                = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<Req<UserResponse>>() {
        });

        return response.getBody();
    }
}
