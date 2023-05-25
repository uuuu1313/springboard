package com.koreait.tests;

import com.koreait.controllers.boards.BoardForm;
import com.koreait.entities.Board;
import com.koreait.entities.BoardData;
import com.koreait.models.board.BoardDataInfoService;
import com.koreait.models.board.BoardDataSaveService;
import com.koreait.models.board.config.BoardConfigInfoService;
import com.koreait.models.board.config.BoardConfigSaveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@Transactional
public class BoardViewTests {

    private Long id; // 게시글 번호
    private Board board;
    @Autowired
    private BoardDataSaveService saveService;
    @Autowired
    private BoardConfigSaveService configSaveService;
    @Autowired
    private BoardConfigInfoService configInfoService;
    @Autowired
    private BoardDataInfoService infoService;

    @BeforeEach
    void init(){
        // 게시판 설정 추가
        com.koreait.controllers.admins.BoardForm boardForm = new com.koreait.controllers.admins.BoardForm();
        boardForm.setBId("freetalk");
        boardForm.setBName("자유게시판");
        configSaveService.save(boardForm);
        board = configInfoService.get(boardForm.getBId(), true);

        // 테스트용 기본 게시글 추가
        BoardForm boardForm2 = BoardForm.builder()
                .bId(board.getBId())
                .gid(UUID.randomUUID().toString())
                .poster("작성자")
                .guestPw("12345678")
                .subject("제목!")
                .content("내용!")
                .category(board.getCategories() == null ? null : board.getCategories()[0])
                .build();

        saveService.save(boardForm2);
        id = boardForm2.getId();
    }


    @Test
    @DisplayName("게시글 조회 성공시 예외 없음")
    void getBoardDataSuccessTest(){
        assertDoesNotThrow(() -> {
           infoService.get(id);
        });
    }
}
