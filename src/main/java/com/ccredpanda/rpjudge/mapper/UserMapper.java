package com.ccredpanda.rpjudge.mapper;

import com.ccredpanda.rpjudge.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/*
*  持久层用户接口
* */
@Mapper
public interface UserMapper {

    // 新增一个用户
    void add(User user);

    // 通过用户ID查询用户是否存在
    Integer countByUsername(String username);

    // 通过用户名获得用户信息
    User getUserByUsername(String username);

    // 通过用户ID获得用户信息
    User getUserById(Long id);

    // 更新用户信息
    void update(User user);

    // 更新用户个人资料
    void updateProfile(@Param("id") Long id,
                       @Param("nickname") String nickname,
                       @Param("signature") String signature,
                       @Param("avatar") String avatar);

    // 用户更新密码
    void updatePassword(@Param("id") Long id, @Param("password") String password);

    // 将用户通过数自增1
    void incSolvedCount(Long userId);

    // 将用户提交数数自增1
    void incSubmitCount(Long userId);
}
