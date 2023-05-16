package com.koreait.controllers.members;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinForm {

    private String userId; // 아이디

    private String userPw; // 비밀번호

    private String userPwRe; // 비밀번호 확인

    private String userNm; // 이름

    private String email; // 이메일

    private String mobile;

    private boolean[] agrees;
}
