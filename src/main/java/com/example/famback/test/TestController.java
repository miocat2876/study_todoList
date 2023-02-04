package com.example.famback.test;

import com.example.famback.fam.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {

    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/test")
    public String Test(){
        return "<div>성공</div>";
    }
//    @GetMapping("/login")
//    public String Login(){
//        return jwtTokenProvider.createToken("테스트", Collections.singletonList("ADMIN"));
//    }
//
//    @GetMapping("/user/login")
//    public String userLogin(){
//        return jwtTokenProvider.createToken("테스트", Collections.singletonList("ADMIN"));
//    }
}
