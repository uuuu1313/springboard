package com.koreait.models.board;

import com.koreait.commons.MemberUtil;
import com.koreait.commons.validators.RequiredValidator;
import com.koreait.commons.validators.Validator;
import com.koreait.controllers.boards.BoardForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BoardValidator implements Validator<BoardForm>, RequiredValidator {

    @Autowired
    private MemberUtil memberUtil;
    @Override
    public void check(BoardForm boardForm) {
        requiredCheck(boardForm.getBId(), new BoardValidationException("BadRequest"));
        requiredCheck(boardForm.getGid(), new BoardValidationException("BadRequest"));
        requiredCheck(boardForm.getPoster(), new BoardValidationException("NotBlank.boardForm.poster"));
        requiredCheck(boardForm.getSubject(), new BoardValidationException("NotBlank.boardForm.subject"));
        requiredCheck(boardForm.getContent(), new BoardValidationException("NotBlank.boardForm.content"));

        // 비회원일때 비회원 비밀번호 체크
        if (!memberUtil.isLogin()){
            requiredCheck(boardForm.getGuestPw(), new BoardValidationException("NotBlank.boardForm.guestPw"));
        }
    }
}
