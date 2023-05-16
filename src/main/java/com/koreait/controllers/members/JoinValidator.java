package com.koreait.controllers.members;

import com.koreait.commons.validator.MobileValidator;
import com.koreait.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class JoinValidator implements Validator, MobileValidator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return JoinForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        /**
         *  1. 아이디 중복 여부
         *  2. 비밀번호 복잡성 체크(알파벳 대소문자, 숫자, 특수문자)
         *  3. 비밀번호, 비밀번호 확인 일치 여부
         *  4. 모바일 형식 체크
         *  5. 모바일 숫자만 추출해서 다시 커맨드 객체에 저장
         *  6. 필수 약관 동의 체크
         */
        JoinForm joinForm = (JoinForm) target;
        String userId = joinForm.getUserId();
        String userPw = joinForm.getUserPw();
        String userPwRe = joinForm.getUserPwRe();
        String mobile = joinForm.getMobile();

        // 1. 아이디 중복 여부
        if (userId != null && !userId.isBlank() && memberRepository.exists(userId)) {
            errors.rejectValue("userId", "Validation.duplicate.userId");
        }


        // 3. 비밀번호와 비밀번호 확인 일치 여부
        if (userPw != null && !userPw.isBlank()
         && userPwRe!= null && !userPwRe.isBlank() && !userPw.equals(userPwRe)){
            errors.reject("userPwRe", "Validation.incorrect.userPwRe");
        }

        // 4. 모바일 형식
    }
}
