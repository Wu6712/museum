package com.yy.museum.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yy.museum.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yy.museum.utils.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yao
 * @since 2023-08-16
 */
public interface IUserService extends IService<User> {

    IPage<User> findList(Integer pageNum, Integer pageSize);

    User register(String username, String password);

    User login(String username, String password);

    User savePassword(Integer id, String oldPassword, String newPassword);

    User savePasswordByName(String username, String oldPassword, String newPassword);

    boolean logout(Integer id);

    IPage<User> findListByName(Integer pageNum, Integer pageSize, String username);

    User saveNicknameByName(String username, String newNickname);

    User saveGenderByName(String username, Integer gender);

    User getUserInfo(Integer id);

    User getUserByName(String username);
}
