package com.koreait.repositories;

import com.koreait.entities.Member;
import com.koreait.entities.QMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {
    Member findByUserId(String userId); // 아이디로 회원 조회

    default boolean exists(String userId){
        QMember member = QMember.member;

        return false;
    }
}
