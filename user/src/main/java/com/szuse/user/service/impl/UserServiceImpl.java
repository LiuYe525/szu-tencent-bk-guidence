package com.szuse.user.service.impl;

import static com.szuse.bkguidance.jooq.tables.User.USER;

import com.google.common.collect.Lists;
import com.szuse.bkguidance.jooq.tables.pojos.User;
import com.szuse.user.service.UserService;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * @author LiuYe
 * @version 1.0
 * @date 15/7/2023 下午4:59
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final DSLContext dsl;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User createUser(String email, String account, String password) {
        password = DigestUtils.sha256Hex((password + account).getBytes(StandardCharsets.UTF_8));
        var result = dsl.insertInto(USER, USER.EMAIL, USER.ACCOUNT, USER.PASSWORD)
                .values(email, account, password)
                .execute();
        if (result != 1) {
            log.error(
                    "create user error due to illegal user information email = '{}', account = "
                            + "'{}'",
                    email, account);
            throw new IllegalArgumentException("create user error");
        }
        log.info("successfully register a user, email '{}' account '{}", email, account);
        return findUser(DSL.condition(USER.EMAIL.eq(email)));
    }

    @Override
    public void removeUserByEmail(String email) {
    }

    @Override
    public void removeUserByAccount(String account) {
    }

    @Override
    public User updateUser(String email, String account, String password, String avatar) {
        return null;
    }

    @Override
    public List<User> listUserByEmail(String email) {
        Condition condition = null;
        if (!ObjectUtils.isEmpty(email)) {
            condition = DSL.condition(USER.EMAIL.eq(email));
        }
        return Lists.newArrayList(listUser(condition));
    }

    private User findUser(Condition condition) {
        return dsl.select().from(USER).where(condition).fetchOne(record -> record.into(User.class));
    }

    private void removeUser(Condition condition) {
        dsl.delete(USER).where(condition).execute();
    }

    private Iterable<User> listUser(Condition condition) {
        log.info("list user with condition = '{}'", condition);
        if (ObjectUtils.isEmpty(condition)) {
            return dsl.select().from(USER).fetch(record -> record.into(User.class));
        }
        return dsl.select().from(USER).where(condition).fetch(record -> record.into(User.class));
    }
}