package com.koreait.restcontrollers;

import com.koreait.commons.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
public class FileUploadController {

    @GetMapping("/file/upload")
    public void upload(){

    }
}
