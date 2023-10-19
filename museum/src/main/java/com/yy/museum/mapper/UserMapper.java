package com.yy.museum.mapper;

import com.yy.museum.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yao
 * @since 2023-08-16
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    User getInfo(Integer id);

    User getInfoByUsername(String username);


    @Update("update user set score = score - 5 where id = #{userId}")
    int puii(Integer userId);

}
