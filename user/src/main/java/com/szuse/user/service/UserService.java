package com.szuse.user.service;

import com.szuse.bkguidance.jooq.tables.pojos.User;

/**
 * @author LiuYe
 * @version 1.0
 * @date 15/7/2023 下午4:59
 */
public interface UserService {

    User createUser(String email, String account, String password);

    void removeUserByEmail(String email);

    void removeUserByAccount(String account);

    User updateUser(String email, String account, String password, String avatar);

    Iterable<User> listUserByEmail(String email);
}
