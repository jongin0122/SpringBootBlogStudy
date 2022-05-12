package com.cos.blog.Test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {

    //http://localhost:8000/blog/temp/home
    @GetMapping("/temp/home")
    public String tempHome(){
        System.out.println("home");

        //@Controller의 기본 파일 리턴 경로 : src/main/resources/static
        return "/home.html";
    }

    @GetMapping("/temp/jsp")
    public String tempJsp(){
        return "test";
    }
}
