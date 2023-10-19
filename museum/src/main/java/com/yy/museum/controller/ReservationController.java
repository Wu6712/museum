package com.yy.museum.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yy.museum.utils.Auth0Jwt;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yy.museum.entity.Reservation;
import com.yy.museum.service.IReservationService;
import com.yy.museum.utils.R;
import com.yy.museum.utils.Result;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
@Api(tags = "预约模块")
@CrossOrigin
@RestController
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    private IReservationService reservationService;

    @Operation(summary = "列表")
    @PostMapping("/list")
    public Result list(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        IPage<Reservation> iPage = reservationService.findList(pageNum, pageSize);
        Map<String, Object> res = new HashMap<>();
        res.put("data", iPage.getRecords());
        res.put("total", iPage.getTotal());
        return R.success("查询成功", res);
    }

    @Operation(summary = "列表2")
    @PostMapping("/list2")
    public Result list2(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;
        List<Reservation> data = reservationService.findList2(pageNum, pageSize);
        Integer total = reservationService.total();
        Map<Object, Object> map = new HashMap<>();
        map.put("data",data);
        map.put("total",total);
        return R.success("查询成功",map);
    }

    @Operation(summary = "用户预约列表")
    @PostMapping("/list/user")
    public Result listByUserId(@RequestParam Integer pageNum, @RequestParam Integer pageSize,@RequestParam Integer userId) {
        IPage<Reservation> iPage = reservationService.findListByUserId(pageNum, pageSize,userId);
        Map<String, Object> res = new HashMap<>();
        res.put("data", iPage.getRecords());
        res.put("total", iPage.getTotal());
        return R.success("查询成功", res);
    }

    @Operation(summary = "用户预约列表")
    @PostMapping("/list/museum")
    public Result listByMuseumId(@RequestParam Integer pageNum, @RequestParam Integer pageSize,@RequestParam Integer museumId) {
        IPage<Reservation> iPage = reservationService.findListByMuseumId(pageNum, pageSize,museumId);
        Map<String, Object> res = new HashMap<>();
        res.put("data", iPage.getRecords());
        res.put("total", iPage.getTotal());
        return R.success("查询成功", res);
    }

    @Operation(summary = "更新")
    @PostMapping("/mod")
    public Result mod(@RequestBody Reservation reservation) {
        return reservationService.updateById(reservation) ? R.success("更新成功") : R.fail("更新失败");
    }

    @Operation(summary = "删除")
    @PostMapping("/remove")
    public Result remove(Integer id) {
        return reservationService.removeById(id) ? R.success("删除成功") : R.fail("删除失败");
    }

    @Operation(summary = "新增")
    @PostMapping("/add")
    public Result add(@RequestBody Reservation reservation) {
        System.out.println(reservation);
        return reservationService.save(reservation) ? R.success("新增成功") : R.fail("新增失败");
    }

    @PostMapping("/addOne")
    public Result addOne(@RequestBody Reservation reservation, HttpServletRequest request) {
        System.out.println(reservation);
        String token = request.getHeader("token");
        System.out.println(token);
        int userId = Auth0Jwt.getUserId(token).intValue();
        reservation.setUserId(userId);
        int i = reservationService.addOne(reservation);

        if(i == 0) {
            return R.fail("预约失败");
        }

        return R.success("预约成功");

    }

    @Operation(summary = "批量删除")
    @PostMapping("/remove/batch/{ids}")
    public Result removeBatch(@PathVariable(name = "ids") List<Integer> ids){
        return reservationService.removeByIds(ids) ? R.success("批量删除成功") : R.fail("批量删除失败");
    }

    @Operation(summary = "已预约")
    @PostMapping("/appointment")
    public Result appointment(@RequestParam Integer id){
        return reservationService.appointment(id) ? R.success("预约成功") : R.fail("预约失败");
    }

    @Operation(summary = "已取消")
    @PostMapping("/cancelAppointment")
    public Result cancelAppointment(@RequestParam Integer id){
        return reservationService.cancelAppointment(id) ? R.success("取消成功") : R.fail("取消失败");
    }

    @Operation(summary = "已开始-签到")
    @PostMapping("/startAppointment")
    public Result startAppointment(@RequestParam Integer id){
        return reservationService.startAppointment(id) ? R.success("开始成功") : R.fail("开始失败");
    }

    @Operation(summary = "已完成-签退")
    @PostMapping("/completeAppointment")
    public Result completeAppointment(@RequestParam Integer id){
        return reservationService.completeAppointment(id) ? R.success("取消成功") : R.fail("取消失败");
    }

    @Operation(summary = "预约总数")
    @GetMapping("/total")
    public Result getTotal() {
        int total = reservationService.getTotal();
        return R.success(total);
    }

    @Operation(summary = "用户预约记录")
    @GetMapping("/listBy")
    public Result ListBy(HttpServletRequest request) {

        String token = request.getHeader("token");
        int userId = Auth0Jwt.getUserId(token).intValue();

        List<Reservation> list = reservationService.getListByUserId(userId);

        if(list == null) {
            return R.fail("查询失败");
        }

        return R.success(list);
    }

    @Operation(summary = "取消预约")
    @PostMapping("/cancel/{id}")
    public Result cancelSign(@PathVariable Integer id) {

        int i = reservationService.cancelSign(id);

        if(i == 0) {
            return R.fail("取消失败");
        }

        return R.success("操作成功");

    }

    @PostMapping("/sign/{id}")
    public Result sign(@PathVariable Integer id) {

        // 获取当前时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = simpleDateFormat.format(date);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // 判断是否超时
        Reservation reservation = reservationService.selectById(id);

        String startTime = reservation.getStartTime().format(formatter);
        String endTime = reservation.getEndTime().format(formatter);

        if(startTime.compareTo(time) > 0) {
            return R.fail("还未到约定时间");
        }

        // 超时
        if(time.compareTo(endTime) > 0) {
            reservationService.updateOne(id);
            reservationService.puii(reservation.getUserId());
            return R.fail("已超时");
        }

        int i = reservationService.sign(id);

        if(i == 0) {
            return R.fail("签到失败");
        }

        return R.success("操作成功");
    }

}

