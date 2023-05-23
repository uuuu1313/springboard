package com.koreait.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity @Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class BoardData extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private String bId;

    private String gid;
    private String poster; // 작성자
    private String guestPw; // 비회원 비밀번호
    private String category; // 게시판 분류
    private String subject; // 제목
    private String content; // 내용
    private int hit; // 조회수
    private String ua; // User-Agent : 브라우저 정보
    private String ip; // 작성자 ip
    private int commentCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userNo")
    private Member member;

}
