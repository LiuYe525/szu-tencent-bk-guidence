package com.szuse.szubkguidance.mapper;


import com.szuse.szubkguidance.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserMapper {
    List<User> findAll();
}
