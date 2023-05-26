package com.koreait.models.board;

import com.koreait.commons.CommonException;
import org.springframework.http.HttpStatus;

public class BoardDataNotExistException extends CommonException {
    public BoardDataNotExistException() {
        super(bundleValidation.getString("Validation.boardData.notExists"), HttpStatus.BAD_REQUEST);
    }
}
