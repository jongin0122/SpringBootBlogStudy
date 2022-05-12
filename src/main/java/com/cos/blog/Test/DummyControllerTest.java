package com.cos.blog.Test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;


//RestController이기 때문에 html을 리턴해주는 것이 아니라 데이터를 리턴해준다.
@RestController
public class DummyControllerTest {

    @Autowired  //의존성 주입
    private UserRepository userRepository;

    //http://localhost:8000/blog/dummy/join (요청)
    //http의 body에 username,password, email 데이터를 가지고 요청
    @PostMapping("/dummy/join")
    public String join(User user){
        System.out.println("username : "+user.getUsername());
        System.out.println("password : "+user.getPassword());
        System.out.println("email : "+user.getPassword());

        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }

    //{id}주소로 파라미터를 전달 받을 수 있다.
    // http://localhost:8000/blog/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id){
        //user/4를 찾으면 내가 데이터베이스에서 못찾아오게 되면 user가 null이 될 것 아냐?
        //그럼 return null이 리턴이 되자나.. 그럼 프로그램에 문제가 있지 않겠니?
        //나는 Optional로 너의 User객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return해!!
        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>(){
            @Override
            public IllegalArgumentException get(){
                return new IllegalArgumentException("해당 유저는 없습니다. id : "+id);
            }
        });    //findById는 리턴값이 Ontional이다.

        /*
        위의 코드를 람다식으로 바꾸면 매우 간단해 진다.
        //람다식
        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("해당 유저는 없습니다.);
        });
         */

        //리턴객체는 Java객체이다. = 자바 오브젝트
        //하지만 요청은 html이 하는데 html을 자바오브젝트를 이해하지 못한다.
        //그래서 변환이 필요하다 (웹브라우저가 이해할 수 있는 데이터 -> JSON)
        //스프링부트는 MessageConverter가 응답시 자동 작동하여서 만약에 자바 오브젝트를 리턴하게 되면
        //MessageConverter가 Jackson이라는 라이브러리를 호출해서 user 오브젝트를 JSON으로 변환해서
        //브라우저에게 전달해 주는 것이다.
        return user;
    }

    //모든 유저 select해보기
    @GetMapping("/dummy/users")
    public List<User> list(){
        return userRepository.findAll();
    }

    //한페이지당 2건의 데이터를 리턴받아 본다.
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
       Page<User> pagingUser = userRepository.findAll(pageable);

       List<User> users=pagingUser.getContent();
       return users;
    }
}
