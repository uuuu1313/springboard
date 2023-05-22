package com.koreait.models.board.config;

import com.koreait.commons.constants.Role;
import com.koreait.controllers.admins.BoardForm;
import com.koreait.entities.Board;
import com.koreait.repositories.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

/**
 * 게시판 설정 추가, 수정
 */
@Service
@RequiredArgsConstructor
public class BoardConfigSaveService {
    private final BoardRepository boardRepository;

    public void save(BoardForm boardForm){
        save(boardForm, null);
    }

    public void save(BoardForm boardForm, Errors errors){

        if (errors != null && errors.hasErrors()){ // Bean Validation 에러 발생 시
            return;
        }

        /**
         *  게시판 설정 조회 -> 없으면 엔티티 생성
         *  게시판 등록 모드인 경우는 중복 여부 체크
         */
        String bId = boardForm.getBId();
        Board board = boardRepository.findById(bId).orElseGet(Board::new);
        String mode = boardForm.getMode();
        if ((mode == null || !mode.equals("update")) && board.getBId() != null) { // 게시판 등록 중복 여부 체크
            throw new DuplicateBoardConfigException();
        }

        board.setBId(bId);
        board.setBName(boardForm.getBName());
        board.setUse(boardForm.isUse());
        board.setRowsOfPage(boardForm.getRowsOfPage());
        board.setShowViewList(boardForm.isShowViewList());
        board.setCategory(boardForm.getCategory());
        board.setListAccessRole(Role.valueOf(boardForm.getListAccessRole()));
        board.setViewAccessRole(Role.valueOf(boardForm.getViewAccessRole()));
        board.setWriteAccessRole(Role.valueOf(boardForm.getWriteAccessRole()));
        board.setReplyAccessRole(Role.valueOf(boardForm.getReplyAccessRole()));
        board.setCommentAccessRole(Role.valueOf(boardForm.getCommentAccessRole()));
        board.setUseEditor(boardForm.isUseEditor());
        board.setUseAttachFile(boardForm.isUseAttachFile());
        board.setUseAttachImage(boardForm.isUseAttachImage());
        board.setLocationAfterWriting(boardForm.getLocationAfterWriting());
        board.setUseReply(boardForm.isUseReply());
        board.setUseComment(boardForm.isUseComment());
        board.setSkin(boardForm.getSkin());

        boardRepository.saveAndFlush(board);

    }
}
