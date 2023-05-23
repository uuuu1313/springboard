package com.koreait.controllers.boards;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardForm {

    private Long id;
    @NotBlank
    private String bId;

    @NotBlank
    private String gid = UUID.randomUUID().toString();
    @NotBlank
    private String poster; // 작성자
    private String guestPw; // 비회원 비밀번호
    private String category; // 게시판 분류
    @NotBlank
    private String subject; // 제목
    @NotBlank
    private String content; // 내용
    private int hit; // 조회수
    private String ua; // User-Agent : 브라우저 정보
    private String ip; // 작성자 ip
    private int commentCnt;

}
