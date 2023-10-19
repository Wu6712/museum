package com.yy.museum.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yy.museum.utils.Auth0Jwt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yy.museum.entity.User;
import com.yy.museum.service.IUserService;
import com.yy.museum.utils.R;
import com.yy.museum.utils.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yao
 * @since 2023-08-16
 */
@Api(tags = "用户模块")
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result register(@ApiParam(value = "用户名", name = "username", required = true) @RequestParam("username") String username,
                           @ApiParam(value = "用户名", name = "password", required = true) @RequestParam("password") String password) {
        User user = userService.register(username, password);
        return user != null ? R.success(user) : R.fail("注册失败");
    }

    @Operation(summary = "用户登入")
    @PostMapping("/login")
    public Result login(@ApiParam(value = "用户名", name = "username", required = true) @RequestParam("username") String username,
                        @ApiParam(value = "密码", name = "password", required = true) @RequestParam("password") String password) {
        User user = userService.login(username, password);
        return user != null ? R.LOGIN_SUCCESS(Auth0Jwt.sign(user.getId(), user.getUsername(), user.getPassword())) : R.fail("登录失败");
    }

    @Operation(summary = "修改密码-id")
    @PostMapping("/info/passwordById")
    public Result savePasswordById(@RequestParam Integer id, @RequestParam String oldPassword, @RequestParam String newPassword) {
        return userService.savePassword(id, oldPassword, newPassword) != null ? R.success("修改成功") : R.fail("修改失败");
    }

    @Operation(summary = "修改密码-name")
    @PostMapping("/info/passwordByName")
    public Result savePasswordByName(@RequestParam String username, @RequestParam String oldPassword, @RequestParam String newPassword) {
        return userService.savePasswordByName(username, oldPassword, newPassword) != null ? R.success("修改成功") : R.fail("修改失败");
    }

    @Operation(summary = "修改昵称")
    @PostMapping("/info/nickname")
    public Result saveNicknameByName(@RequestParam String username,@RequestParam String newNickname){
        return userService.saveNicknameByName(username, newNickname) != null ? R.success("修改成功") : R.fail("修改失败");
    }

    @Operation(summary = "修改性别")
    @PostMapping("/info/gender")
    public Result saveGengerByName(@RequestParam String username,@RequestParam Integer gender){
        return userService.saveGenderByName(username, gender) != null ? R.success("修改成功") : R.fail("修改失败");
    }

    @Operation(summary = "修改用户信息")
    @PutMapping()
    public Result updateUser(@RequestBody User user) {

        return userService.updateById(user) ? R.success("修改成功") : R.fail("修改失败");

    }

    @Operation(summary = "用户登出")
    @PostMapping("/logout")
    public Result logout(Integer id) {
        return userService.logout(id) ? R.success("退出成功") : R.fail("退出失败");
    }

    @Operation(summary = "用户注销")
    @PostMapping("/logoff")
    public Result logoff(Integer id) {
        return userService.removeById(id) ? R.success("注销成功") : R.fail("注销失败");
    }

    @Operation(summary = "用户更新")
    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        return userService.updateById(user) ? R.success("更新成功") : R.fail("更新失败");
    }

    @Operation(summary = "获取一个")
    @GetMapping("/one")
    public Result one(Integer id) {
        return userService.getById(id) !=null ? R.success("获取成功",userService.getById(id)) : R.fail("获取失败");
    }

    @Operation(summary = "用户列表")
    @GetMapping("/list")
    public Result list(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        IPage<User> userPage = userService.findList(pageNum, pageSize);
        Map<String, Object> res = new HashMap<>();
        res.put("data", userPage.getRecords());
        res.put("total", userPage.getTotal());
        return R.success("查询成功", res);
    }

    @Operation(summary = "名字模糊查询")
    @GetMapping("/fuzzyList")
    public Result fuzzyList(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam String username) {
        IPage<User> userPage = userService.findListByName(pageNum, pageSize, username);
        Map<String, Object> res = new HashMap<>();
        res.put("data", userPage.getRecords());
        res.put("total", userPage.getTotal());
        return R.success("查询成功", res);
    }

    @Operation(summary = "批量删除")
    @PostMapping("/remove/batch/{ids}")
    public Result removeBatch(@PathVariable(name = "ids") List<Integer> ids){
        return userService.removeByIds(ids) ? R.success("批量删除成功") : R.fail("批量删除失败");
    }

    @Operation(summary = "用户信息")
    @GetMapping
    public Result getInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        Integer id = Auth0Jwt.getUserId(token).intValue();
        System.out.println(id);

        User userInfo = userService.getUserInfo(id);

        if (userInfo == null) {
            return R.fail("查询失败");
        }

        return R.success(userInfo);

    }

    @Operation(summary = "用户信息byName")
    @GetMapping("/info")
    public Result getInfoByName(@RequestParam(name = "username") String username) {

        User user = userService.getUserByName(username);

        if (user == null) {
            return R.fail("查询失败");
        }
        return R.success(user);
    }
}

