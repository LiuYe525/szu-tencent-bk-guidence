package com.szuse.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.szuse.common.base.service.impl.BaseCrudServiceImpl;
import com.szuse.common.base.service.impl.RedisCacheClient;
import com.szuse.user.entity.User;
import com.szuse.user.mapper.UserMapper;
import com.szuse.user.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @author LiuYe
 * @version 1.0
 * @date 5/5/2023 下午9:02
 */
@Service
public class UserServiceImpl extends BaseCrudServiceImpl<UserMapper, User> implements IUserService {

    public UserServiceImpl(RedisCacheClient cacheClient) throws InstantiationException, IllegalAccessException {
        super(cacheClient);
    }
}
