package com.spring.jwt_auth_prog;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class controller {


    @RequestMapping("/welcome")
    public String welcome(){
        String toReturn = "This Page is Private";

        return toReturn;
    }

}
