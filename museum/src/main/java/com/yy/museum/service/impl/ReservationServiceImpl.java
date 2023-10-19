package com.yy.museum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yy.museum.entity.Reservation;
import com.yy.museum.entity.vo.ReservationVo;
import com.yy.museum.mapper.ReservationMapper;
import com.yy.museum.mapper.UserMapper;
import com.yy.museum.service.IReservationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yao
 * @since 2023-08-16
 */
@Service
public class ReservationServiceImpl extends ServiceImpl<ReservationMapper, Reservation> implements IReservationService {

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public IPage<Reservation> findList(Integer pageNum, Integer pageSize) {
        return this.page(new Page<>(pageNum,pageSize));
    }

    @Override
    public List<Reservation> findList2(Integer pageNum, Integer pageSize) {
//        Page<Reservation> page = new Page<>(pageNum, pageSize);
//        Page<Reservation> iPage = reservationMapper.selectPage(page, null);
//        return iPage;
        return reservationMapper.getList(pageNum, pageSize);
    }

    @Override
    public Integer total() {
        return reservationMapper.total();
    }

    @Override
    public boolean appointment(Integer id) {
        UpdateWrapper<Reservation> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper.set("status",2);
        if(reservationMapper.update(null,updateWrapper) == 1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean cancelAppointment(Integer id) {
        UpdateWrapper<Reservation> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper.set("status",1);
        if(reservationMapper.update(null,updateWrapper) == 1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean startAppointment(Integer id) {
        UpdateWrapper<Reservation> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        LocalDateTime now = LocalDateTime.now();
        updateWrapper.set("status",2);
        updateWrapper.set("start_time",now);
        if(reservationMapper.update(null,updateWrapper) == 1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean completeAppointment(Integer id) {
        UpdateWrapper<Reservation> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        LocalDateTime now = LocalDateTime.now();
        updateWrapper.set("status",3);
        updateWrapper.set("end_time",now);
        if(reservationMapper.update(null,updateWrapper) == 1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public IPage<Reservation> findListByUserId(Integer pageNum, Integer pageSize,Integer userId) {
        return this.page(new Page<>(pageNum,pageSize),new QueryWrapper<Reservation>().eq("user_id",userId));
    }

    @Override
    public IPage<Reservation> findListByMuseumId(Integer pageNum, Integer pageSize, Integer museumId) {
        return this.page(new Page<>(pageNum,pageSize),new QueryWrapper<Reservation>().eq("museum_id",museumId));
    }

    @Override
    public int addOne(Reservation reservation) {
        reservation.setStatus(1);
        return reservationMapper.saveOne(reservation);
    }

    @Override
    public int getTotal() {
        return reservationMapper.getTotal();
    }

    @Override
    public List<Reservation> getListByUserId(Integer userId) {
        return reservationMapper.getListByUserId(userId);
    }

    @Override
    public int cancelSign(Integer id) {
        return reservationMapper.updateStatusById(id);
    }

    @Override
    public int sign(Integer id) {
        return reservationMapper.sign(id);
    }

    @Override
    public Reservation selectById(Integer id) {
        return reservationMapper.selectById(id);
    }

    @Override
    public int puii(Integer userId) {

        return userMapper.puii(userId);

    }

    @Override
    public int updateOne(Integer id) {
        return reservationMapper.updateOne(id);
    }
}
