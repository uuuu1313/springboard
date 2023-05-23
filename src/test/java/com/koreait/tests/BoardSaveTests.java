package com.koreait.tests;

import com.koreait.controllers.boards.BoardForm;
import com.koreait.controllers.members.JoinForm;
import com.koreait.entities.Board;
import com.koreait.models.board.BoardDataSaveService;
import com.koreait.models.board.config.BoardConfigInfoService;
import com.koreait.models.board.config.BoardConfigSaveService;
import com.koreait.models.member.MemberSaveService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
public class BoardSaveTests {

    @Autowired
    private BoardDataSaveService saveService;

    @Autowired
    private BoardConfigSaveService configSaveService;

    @Autowired
    private BoardConfigInfoService configInfoService;

    @Autowired
    private MemberSaveService memberSaveService;

    private Board board;

    private JoinForm joinForm;

    @BeforeEach
    @Transactional
    void init(){
        // 게시판 설정 추가
        com.koreait.controllers.admins.BoardForm boardForm = new com.koreait.controllers.admins.BoardForm();
        boardForm.setBId("freetalk");
        boardForm.setBName("자유게시판");
        configSaveService.save(boardForm);

        board = configInfoService.get(boardForm.getBId(), true);
        //회원 가입 추가
        JoinForm joinForm = JoinForm.builder()
                .userId("user01")
                .userPw("aA!123456")
                .userPwRe("aA!123456")
                .email("user01@test.org")
                .userNm("사용자01")
                .mobile("01000000000")
                .agrees(new boolean[]{true})
                .build();
        memberSaveService.save(joinForm);
    }

    private BoardForm getGuestBoardForm(){
        return BoardForm.builder()
                .bId(board.getBId())
                .guestPw("12345678")
                .poster("비회원")
                .subject("제목!")
                .content("내용")
                .category(board.getCategories() == null ? null : board.getCategories()[0])
                .build();
    }

    @WithMockUser(username="user01", password = "aA!123456")
    private BoardForm getMemberBoardForm(){
        return BoardForm.builder()
                .bId(board.getBId())
                .poster(joinForm.getUserNm())
                .subject("제목!")
                .content("내용")
                .category(board.getCategories() == null ? null : board.getCategories()[0])
                .build();
    }

    @Test
    @DisplayName("게시글 등록(비회원) 성공시 예외 없음")
    void registerSuccessTest(){
        assertDoesNotThrow(() -> {
            saveService.save(getGuestBoardForm());
        });

    }
}
