package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

//ORM->Java(다른언어) Object -> 테이블로 매핑해주는 기술
//User 클래스가 MySQL에 테이블이 생성이 된다.

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@DynamicInsert  //insert시에 null인 필드를 제외시켜준다.
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
    private int id; //시퀀스, auto_increment

    @Column(nullable = false, length = 30, unique = true)
    private String username; //아이디

    @Column(nullable = false, length = 100) //해쉬 비밀번호 암호화를 위해 넉넉하게 잡아준다.
    private String password;

    @Column(nullable = false, length = 50)
    private String email;


    // Enum을 쓰는게 좋다.
    // role이란 이사람이 ADMIN인지 USER인지 권한부여용 변수
    // String이면 실수로 managerrrrr가 들어갈 수 있는데, Enum을 써서 도메인 설정을 하는 것이 좋다.
    //@ColumnDefault("'user'")
    @Enumerated(EnumType.STRING) //DB는 enum타입이 없기 때문에 사용하는 어노테이션이다.
    private RoleType role;

    @CreationTimestamp //시간이 자동으로 입력된다.
    private Timestamp createDate;
}
