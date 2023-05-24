package com.koreait.models.board;

import com.koreait.controllers.boards.BoardForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardDataSaveService {

    private final BoardValidator validator;

    public void save(BoardForm boardForm){
        validator.check(boardForm);
    }
}
