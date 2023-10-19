package com.yy.museum.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yy.museum.entity.Notice;
import com.yy.museum.service.INoticeService;
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
@Api(tags = "公告模块")
@CrossOrigin
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private INoticeService noticeService;

    @Operation(summary = "列表")
    @PostMapping("/list")
    public Result list(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        IPage<Notice> iPage = noticeService.findList(pageNum, pageSize);
        Map<String, Object> res = new HashMap<>();
        res.put("data", iPage.getRecords());
        res.put("total", iPage.getTotal());
        return R.success("查询成功", res);
    }

    @Operation(summary = "更新")
    @PostMapping("/mod")
    public Result mod(@RequestBody Notice notice) {
        return noticeService.updateById(notice) ? R.success("更新成功") : R.fail("更新失败");
    }

    @Operation(summary = "删除")
    @PostMapping("/remove")
    public Result remove(Integer id) {
        return noticeService.removeById(id) ? R.success("删除成功") : R.fail("删除失败");
    }

    @Operation(summary = "新增")
    @PostMapping("/add")
    public Result add(@RequestBody Notice notice) {
        return noticeService.save(notice) ? R.success("新增成功") : R.fail("新增失败");
    }

    @Operation(summary = "批量删除")
    @PostMapping("/remove/batch/{ids}")
    public Result removeBatch(@PathVariable(name = "ids") List<Integer> ids){
        return noticeService.removeByIds(ids) ? R.success("批量删除成功") : R.fail("批量删除失败");
    }
}

