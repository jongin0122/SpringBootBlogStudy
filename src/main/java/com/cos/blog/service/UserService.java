package com.cos.blog.service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//스프링이 컴포넌트 스캔을 통해서 Bean에 등록 해준다. ->IoC를 해준다.
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //트랜잭션으로 하나로 묶었기 때문에 여기서 전체가 성공을 하면 commit이 될 것이고
    //하나라도 실패를 한다면 rollback이 될 것이다.
    @Transactional
    public void 회원가입(User user){
        userRepository.save(user);
    }

    @Transactional(readOnly = true) // Select할 때 트랜젝션이 시작, 서비스가 종료될 때 트랜잭션 종료 (이때까지 정합성 유지)
    public User 로그인(User user){
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }
}
