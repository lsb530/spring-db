package com.boki.jdbc.connection;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.boki.jdbc.connection.ConnectionConst.*;

@Slf4j
public class ConnectionTest {

    @Test
    void driverManager() throws SQLException {
        Connection con1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Connection con2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        log.info("connection={}, class={}", con1, con1.getClass());
        log.info("connection={}, class={}", con2, con2.getClass());
    }

    @Test
    void dataSourceDriverManager() throws SQLException {
        // DriverManagerDataSource - 항상 새로운 커넥션을 획득
        // DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

        useDataSource(dataSource, 2);
    }

    @Test
    void dataSourceConnectionPool() throws SQLException, InterruptedException {
        // 커넥션 풀링
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setConnectionTimeout(5_000L); // default: 30초
        dataSource.setMaximumPoolSize(10); // default: 10
        dataSource.setPoolName("MyPool");

        useDataSource(dataSource, 2);

        // 기본 PoolSize를 초과한 요청을 할 시 ConnectionTimeout까지 기다리다 SQLTransientConnectionException 발생
        // useDataSource(dataSource, 11);
        Thread.sleep(1000L);
    }

    private void useDataSource(DataSource dataSource, int connCountReq) throws SQLException {
        for (int i = 0; i < connCountReq; i++) {
            Connection conn = dataSource.getConnection();
            log.info("connection={}, class={}", conn, conn.getClass());
        }
    }
}
