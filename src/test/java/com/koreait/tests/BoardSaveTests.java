package com.koreait.tests;

import com.koreait.models.board.BoardValidationException;
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
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@Transactional
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


    @Test
    @DisplayName("게시글 등록 ( 회원 ) 성공시 예외 없음")
    void registerMemberSuccessTest() {
        assertDoesNotThrow(() -> {
            saveService.save(getMemberBoardForm());
        });
    }

    // 공통(회원, 비회원) 유효성 검사 체크
    private void commonRequiredFieldsTest(){
        assertAll(
                // bId - null
                () -> assertThrows(BoardValidationException.class, () -> {
                    BoardForm boardForm = getGuestBoardForm();
                    boardForm.setBId(null);
                    saveService.save(boardForm);
                }),
                // bId - 공백
                () -> assertThrows(BoardValidationException.class, () -> {
                    BoardForm boardForm = getGuestBoardForm();
                    boardForm.setBId("    ");
                    saveService.save(boardForm);
                }),
                // gid - null
                () -> assertThrows(BoardValidationException.class, () -> {
                    BoardForm boardForm = getGuestBoardForm();
                    boardForm.setGid(null);
                    saveService.save(boardForm);
                }),
                // gid - 공백
                () -> assertThrows(BoardValidationException.class, () -> {
                    BoardForm boardForm = getGuestBoardForm();
                    boardForm.setGid("    ");
                    saveService.save(boardForm);
                }),
                // poster - null
                () -> assertThrows(BoardValidationException.class, () -> {
                    BoardForm boardForm = getGuestBoardForm();
                    boardForm.setPoster(null);
                    saveService.save(boardForm);
                }),
                // poster - 공백
                () -> assertThrows(BoardValidationException.class, () -> {
                    BoardForm boardForm = getGuestBoardForm();
                    boardForm.setPoster("      ");
                    saveService.save(boardForm);
                }),
                // subject - null
                () -> assertThrows(BoardValidationException.class, () -> {
                    BoardForm boardForm = getGuestBoardForm();
                    boardForm.setSubject(null);
                    saveService.save(boardForm);
                }),
                // subject - 공백
                () -> assertThrows(BoardValidationException.class, () -> {
                    BoardForm boardForm = getGuestBoardForm();
                    boardForm.setSubject("       ");
                    saveService.save(boardForm);
                }),
                // content - null
                () -> assertThrows(BoardValidationException.class, () -> {
                    BoardForm boardForm = getGuestBoardForm();
                    boardForm.setContent(null);
                    saveService.save(boardForm);
                }),
                // content - 공백
                () -> assertThrows(BoardValidationException.class, () -> {
                    BoardForm boardForm = getGuestBoardForm();
                    boardForm.setContent("     ");
                    saveService.save(boardForm);
                })

        );

    }

    @Test
    @DisplayName("필수 항목 검증(비회원) - bId, gid, poster, subject, content, guestPw(6자리 이상), BoardValidationException 발생")
    @WithAnonymousUser
    void requiredFieldsGuestTest(){
        commonRequiredFieldsTest();

        assertAll(
                // guestPw - null
                () -> assertThrows(BoardValidationException.class, () -> {
                    BoardForm boardForm = getGuestBoardForm();
                    boardForm.setGuestPw(null);
                    saveService.save(boardForm);
                }),
                // content - 공백
                () -> assertThrows(BoardValidationException.class, () -> {
                    BoardForm boardForm = getGuestBoardForm();
                    boardForm.setGuestPw("       ");
                    saveService.save(boardForm);
                }),
                () -> assertThrows(BoardValidationException.class, () -> {
                    BoardForm boardForm = getGuestBoardForm();
                    boardForm.setGuestPw("1234");
                    saveService.save(boardForm);
                })
        );
    }

    @Test
    @DisplayName("필수 항목 검증(회원) - bId, gid, poster, subject, content, BoardValidationException 발생")
    @WithMockUser(username = "user01", password = "aA!123456")
    void requiredFieldsMemberTest(){
        commonRequiredFieldsTest();
    }



}
