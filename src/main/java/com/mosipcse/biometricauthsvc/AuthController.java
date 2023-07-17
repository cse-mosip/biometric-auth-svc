package com.mosipcse.biometricauthsvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    AuthController() {

    }
    @GetMapping("/test")
    String testEndpoint(){
        return "Hello World" ;
    }

}
