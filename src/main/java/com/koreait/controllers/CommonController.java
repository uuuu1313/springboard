package com.koreait.controllers;

import com.koreait.commons.CommonException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("com.koreait.controllers")
public class CommonController {


    @ExceptionHandler(Exception.class) // <= Exception.class 타입의 에러가 발생하면 해당 메서드 실행 됨
    public String errorHandler(Exception e, Model model, HttpServletRequest request, HttpServletResponse response){
        int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

        if (e instanceof CommonException){
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus().value();
        }

        response.setStatus(status);
        String URL = request.getRequestURI();

        model.addAttribute("status", status);
        model.addAttribute("path", URL);
        model.addAttribute("message", e.getMessage());
        model.addAttribute("exception", e);

        e.printStackTrace();

        return "error/common";
    }
}
