package com.koreait.models.board.config;

import com.koreait.commons.CommonException;
import org.springframework.http.HttpStatus;

public class BoardNotAllowAccessException extends CommonException {
    public BoardNotAllowAccessException(){
        super(bundleValidation.getString("Validation.Board.NotAllowAccess"), HttpStatus.UNAUTHORIZED);
    }
}
