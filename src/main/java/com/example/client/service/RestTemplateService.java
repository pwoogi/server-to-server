package com.example.client.service;

import com.example.client.dto.UserResponse;
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

}
