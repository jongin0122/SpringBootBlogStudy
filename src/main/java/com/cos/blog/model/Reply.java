package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reply {

    @Id
    private int id;

    @Column(nullable = false, length=200)
    private String content;

    @ManyToOne  //Many는 답변 One은 게시글 --> 하나의 게시글에는 여러개의 답변이 올 수 있다.
    @JoinColumn(name="boardId")
    private Board board;

    @ManyToOne //여러개의 답변은 하나의 유저가 쓸 수 있다.
    @JoinColumn(name="userId")
    private User user;

    @CreationTimestamp
    private Timestamp createDate;
}
