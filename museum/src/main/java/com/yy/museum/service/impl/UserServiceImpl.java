package com.yy.museum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yy.museum.entity.User;
import com.yy.museum.mapper.UserMapper;
import com.yy.museum.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yao
 * @since 2023-08-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public IPage<User> findList(Integer pageNum, Integer pageSize) {
        return this.page(new Page<>(pageNum,pageSize));
    }

    @Override
    public User register(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password.trim()));
        this.save(user);
        return user;
    }

    @Override
    public User login(String username, String password) {
        User user = this.getOne(new QueryWrapper<User>().lambda().eq(User::getUsername, username.toLowerCase().trim()));
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username", username);
        updateWrapper.set("online", 1);
        int update = userMapper.update(null, updateWrapper);
        if (new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            if (update == 1) {
                return user;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public User savePassword(Integer id, String oldPassword, String newPassword) {
        User user = this.getOne(new QueryWrapper<User>().lambda().eq(User::getId, id));
        if (user != null && new BCryptPasswordEncoder().matches(oldPassword, user.getPassword())) {
            user.setPassword(new BCryptPasswordEncoder().encode(newPassword.trim()));
            userMapper.updateById(user);
            return user;
        } else {
            return null;
        }
    }

    @Override
    public User savePasswordByName(String username, String oldPassword, String newPassword) {
        User user = this.getOne(new QueryWrapper<User>().lambda().eq(User::getUsername, username));
        if (user != null && new BCryptPasswordEncoder().matches(oldPassword, user.getPassword())) {
            user.setPassword(new BCryptPasswordEncoder().encode(newPassword.trim()));
            userMapper.updateById(user);
            return user;
        } else {
            return null;
        }
    }

    @Override
    public boolean logout(Integer id) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        updateWrapper.set("online", 0);
        int update = userMapper.update(null, updateWrapper);
        if (update == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public IPage<User> findListByName(Integer pageNum, Integer pageSize, String username) {
        return this.page(new Page<>(pageNum, pageSize), new QueryWrapper<User>().like("username", username));
    }

    @Override
    public User saveNicknameByName(String username, String newNickname) {
        User user = this.getOne(new QueryWrapper<User>().lambda().eq(User::getUsername, username));
        if (user != null) {
            user.setNickname(newNickname);
            userMapper.updateById(user);
            return user;
        } else {
            return null;
        }
    }

    @Override
    public User saveGenderByName(String username, Integer gender) {
        User user = this.getOne(new QueryWrapper<User>().lambda().eq(User::getUsername, username));
        if (user != null) {
            user.setSex(gender);
            userMapper.updateById(user);
            return user;
        } else {
            return null;
        }
    }

    @Override
    public User getUserInfo(Integer id) {
        return userMapper.getInfo(id);
    }

    @Override
    public User getUserByName(String username) {
        return userMapper.getInfoByUsername(username);
    }
}
