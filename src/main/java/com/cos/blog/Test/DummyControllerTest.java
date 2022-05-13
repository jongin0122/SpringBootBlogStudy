package com.cos.blog.Test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
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

    //email과 password를 받아보자.
    //json 데이터를 요청 -> Java Object로 변환해서 받아준다.
    //Message Converter의 Jackson라이브러리가 변환해서 받아준다.
    @Transactional  //간단한 이해 : @Transactional을 걸면 save를 하지 않아도 업데이트가 된다. 함수 종료 시에 자동 commit됨
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id,@RequestBody User requestUser){
        System.out.println("id : " + id);
        System.out.println("password : " + requestUser.getPassword());
        System.out.println("email : " + requestUser.getEmail());

        //null값이 없는 기존의 꽉찬 user객체를 찾고
        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("수정에 실패하였습니다.");
        });

        //그 user객체에 @RequestBody로 받아온 requestUser.getPassword()값을 저장(set)해주면
        //꽉찬 데이터에 해당 데이터만 저장되므로 null값이 존재하지 않아서 update형식이 된다.
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());

        //save는 insert할 때 주로 쓴다.
        //update 때 쓰려고 하면 넣지 않는 것 (username,role)은 Null로 변환해 버리는 문제가 있다.
        //save함수는 id를 전달하지 않으면 insert를 해주고
        //save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
        //save함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 해준다.
        //userRepository.save(user);

        //더티 체킹
        return user;
    }

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id){
        try{
            userRepository.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
        }

        return "삭제 되었습니다. id :" + id;
    }
}
