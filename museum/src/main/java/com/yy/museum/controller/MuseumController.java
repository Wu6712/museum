package com.yy.museum.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yy.museum.entity.Museum;
import com.yy.museum.service.IMuseumService;
import com.yy.museum.utils.R;
import com.yy.museum.utils.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yao
 * @since 2023-08-16
 */
@Api(tags = "博物馆模块")
@CrossOrigin
@RestController
@RequestMapping("/museum")
public class MuseumController {
    @Autowired
    private IMuseumService museumService;

    @Operation(summary = "列表")
    @PostMapping("/list")
    public Result list(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        IPage<Museum> iPage = museumService.findList(pageNum, pageSize);
        Map<String, Object> res = new HashMap<>();
        res.put("data", iPage.getRecords());
        res.put("total", iPage.getTotal());
        return R.success("查询成功", res);
    }

    @Operation(summary = "更新")
    @PostMapping("/mod")
    public Result mod(@RequestBody Museum museum) {
        return museumService.updateById(museum) ? R.success("更新成功") : R.fail("更新失败");
    }

    @Operation(summary = "删除")
    @PostMapping("/remove")
    public Result remove(Integer id) {
        return museumService.removeById(id) ? R.success("删除成功") : R.fail("删除失败");
    }

    @Operation(summary = "新增")
    @PostMapping("/add")
    public Result add(@RequestBody Museum museum) {
        return museumService.save(museum) ? R.success("新增成功") : R.fail("新增失败");
    }

    @Operation(summary = "批量删除")
    @PostMapping("/remove/batch")
    public Result removeBatch(List<Integer> ids){
        return museumService.removeByIds(ids) ? R.success("批量删除成功") : R.fail("批量删除失败");
    }

    @Operation(summary = "获取一个")
    @GetMapping("/one")
    public Result one(Integer id) {
        return museumService.getById(id) !=null ? R.success("获取成功",museumService.getById(id)) : R.fail("获取失败");
    }
}

