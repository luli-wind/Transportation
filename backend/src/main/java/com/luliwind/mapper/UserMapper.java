package com.luliwind.mapper;

import com.luliwind.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {


    //todo 邮箱和用户名不能重复，注册的时候需要记得验证
    @Select("select * from users where username = #{text} or email = #{text}")
    User findUserByUserNameOrEmail(String text);
}
