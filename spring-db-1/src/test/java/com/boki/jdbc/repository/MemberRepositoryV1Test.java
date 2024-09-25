package com.boki.jdbc.repository;

import com.boki.jdbc.connection.ConnectionConst;
import com.boki.jdbc.domain.Member;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.*;

import static com.boki.jdbc.connection.ConnectionConst.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@Slf4j
class MemberRepositoryV1Test {

    MemberRepositoryV1 repository;

    @BeforeEach
    void beforeEach() {
        // 기본 DriverManager - 항상 새로운 커넥션을 획득
        // DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

        // 커넥션 풀링
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        repository = new MemberRepositoryV1(dataSource);
    }

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

        Thread.sleep(1000L);
    }

    @Test
    void multiThreadCrud() throws Exception {
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        ArrayList<Callable<Void>> tasks = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            final int idx = i;
            tasks.add(() -> {
                String memberId = "member" + idx;
                Member member = new Member(memberId, 1_000 * (idx + 1));

                //save
                repository.save(member);

                // 일부러 지연을 추가
                Thread.sleep(100); // 여러개의 connection이 사용되도록

                // findById
                Member findMember = repository.findById(memberId);
                log.info("Thread: {}, findMember={}", Thread.currentThread().getName(), findMember);
                assertEquals(findMember, member);

                // update: money: 10000 -> 20000
                repository.update(memberId, 20_000);
                Member updatedMember = repository.findById(memberId);
                assertEquals(20_000, updatedMember.getMoney());

                // delete
                repository.delete(memberId);
                assertThrowsExactly(NoSuchElementException.class,
                    () -> repository.findById(memberId)
                );

                return null;
            });
        }

        // 모든 작업을 실행하고 결과를 기다림
        List<Future<Void>> futures = executorService.invokeAll(tasks);

        // 작업 결과 확인 (예외 발생 시 throw)
        for (Future<Void> future : futures) {
            future.get();
        }

        // 스레드 풀 종료
        executorService.shutdown();
        if (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
            executorService.shutdownNow();
        }
    }

}
