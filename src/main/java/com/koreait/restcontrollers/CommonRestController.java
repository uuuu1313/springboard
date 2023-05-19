package com.koreait.restcontrollers;

import com.koreait.commons.CommonException;
import com.koreait.commons.rests.JSONData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.koreait.restcontrollers")
public class CommonRestController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JSONData<Object>> errorHandler(Exception e){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (e instanceof CommonException){
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus();
        }

        JSONData<Object> jsonData = JSONData.builder()
                .success(false)
                .message(e.getMessage())
                .status(status)
                .build();

        return ResponseEntity.status(status).body(jsonData);
        //ResponseEntity .status = 지정된 http 상태 코드 생성
        // .body(T body) = 지정된 본문 데이터 생성
    }
}
