package com.boki.jdbc.repository;

import com.boki.jdbc.domain.Member;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws Exception {
        Member member = new Member("memberV0", 10_000);
        repository.save(member);
    }

}
