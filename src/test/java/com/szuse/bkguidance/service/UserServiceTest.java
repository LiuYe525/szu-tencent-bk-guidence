package com.szuse.bkguidance.service;

import java.nio.charset.StandardCharsets;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class UserServiceTest {

    @Test
    void testAuthorize() {
        String account = "user";
        String password = "password";
        String firstEncodePassword = DigestUtils.sha256Hex(
                (password + account).getBytes(StandardCharsets.UTF_8));
        log.info("account: {}, first password before encode: {}", account, firstEncodePassword);
        String secondEncodePassword = DigestUtils.sha256Hex(
                (password + account).getBytes(StandardCharsets.UTF_8));
        log.info("account: {}, second password after encode: {}", account, secondEncodePassword);
        Assertions.assertEquals(firstEncodePassword, secondEncodePassword);
    }
}
