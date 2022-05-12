package com.cos.blog.Test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청----> 응답(HTML)
//@Controller

//사용자가 요청----> 응답(Data)
@RestController
public class HttpControllerTest {

    private final static String TAG="HttpControllerTest : ";

    @GetMapping("/http/lombok")
    public String lombokTest(){
        Member m = Member.builder().id(1).password(1234).email("jongin@naver.com").username("ssal").build();
        System.out.println(TAG + "getter : " + m.getId());
        m.setId(5000);
        System.out.println(TAG + "setter : " + m.getId());
        return "lombok test 완료";
    }

    //http://localhost:8080/http/get (select)
    @GetMapping("/http/get")
    public String getTest(Member m){
        return "get 요청" + m.getId() +", "+ m.getUsername()+", " + m.getPassword()+", "+m.getEmail();
    }

    //인터넷 브라우저요청은 get요청밖에 할 수 없다.
    //http://localhost:8080/http/post (insert)
    @GetMapping("/http/post")
    public String postTest(@RequestBody Member m){ //MessageConverter클래스가 자동으로 매핑해준다 (스프링부트)
        return "post 요청" + + m.getId() +", "+ m.getUsername()+", " + m.getPassword()+", "+m.getEmail();
    }

    //http://localhost:8080/http/update (update)
    @GetMapping("/http/put")
    public String putTest(){
        return "put 요청";
    }

    //http://localhost:8080/http/delete (delete)
    @GetMapping("/http/delete")
    public String deleteTest(){
        return "delete 요청";
    }
}
