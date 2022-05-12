package com.cos.blog.Test;

import lombok.*;

@Data
@NoArgsConstructor
public class Member {

    //변수의 상태는 직접 접근하면 안되기 때문에 private로 설정한다.
    //변수의 접근은 method를 통하여 하기 위해서 getter, setter를 생성한다.
    private int id;
    private String username;
    private int password;
    private String email;

    //원하는 매개변수만 써서 생성자를 만들 수 있도록 한다.
    //id가 없이 객체를 만드려면 public Member(....id없이) 만들어야 된다. 하지만
    //Builder를 쓰면 여러 매개변수 생성자를 오버로딩하여 만들어 주며, 객체를 만들 때 순서와 상관없이 만들 수 있다.
    //ex)Member m = Member.builder().username("ssal").id(1).build() <-----id와 이름의 순서가 바뀌었지만 생성자를 만들어준다.
    //pssword와 email이 없는데도 만들어준다.(오버로딩)
    @Builder
    public Member(int id, String username, int password, String email){
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
