package com.cos.blog.repository;

import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//DAO
//자동으로 Bean등록이 된다.
//@Repository 생략 가능
public interface UserRepository extends JpaRepository<User, Integer> {
    //JPA naming 쿼리
    //SELECT * FROM user WHERE username= ? AND password = ? 여기서 ?에는 아래 파라미터가 들어간다.
    //User findByUsernameAndPassword(String username, String password);
    //User login(String username, String password);


    //SELECT * FROM user WHERE username = 1?;
    Optional<User> findByUsername(String username);
}
