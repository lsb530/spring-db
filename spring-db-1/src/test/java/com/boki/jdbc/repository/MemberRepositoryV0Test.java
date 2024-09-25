package com.boki.jdbc.repository;

import com.boki.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws Exception {
        // save
        Member member = new Member("memberV100", 10_000);
        repository.save(member);

        // findById
        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember={}", findMember);
        log.info("member == findMember {}", member == findMember);
        log.info("member equals findMember {}", member.equals(findMember));
        assertEquals(findMember, member);

        // update: money: 10000 -> 20000
        repository.update(member.getMemberId(), 20_000);
        Member updatedMember = repository.findById(member.getMemberId());
        // assertEquals(updatedMember, member); // 실수하지 마세요~
        assertEquals(20_000, updatedMember.getMoney());

        // delete
        repository.delete(member.getMemberId());
        assertThrowsExactly(NoSuchElementException.class,
            () -> repository.findById(member.getMemberId())
        );
    }

}
