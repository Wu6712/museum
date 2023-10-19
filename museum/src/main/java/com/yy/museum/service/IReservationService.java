package com.yy.museum.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yy.museum.entity.Reservation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yy.museum.entity.vo.ReservationVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yao
 * @since 2023-08-16
 */
public interface IReservationService extends IService<Reservation> {

    IPage<Reservation> findList(Integer pageNum, Integer pageSize);

    List<Reservation> findList2(Integer pageNum, Integer pageSize);

    Integer total();

    boolean appointment(Integer id);

    boolean cancelAppointment(Integer id);

    boolean startAppointment(Integer id);

    boolean completeAppointment(Integer id);

    IPage<Reservation> findListByUserId(Integer pageNum, Integer pageSize,Integer userId);

    IPage<Reservation> findListByMuseumId(Integer pageNum, Integer pageSize, Integer museumId);

    int addOne(Reservation reservation);

    int getTotal();

    List<Reservation> getListByUserId(Integer userId);

    int cancelSign(Integer id);

    int sign(Integer id);

    Reservation selectById(Integer id);

    int puii(Integer userId);

    int updateOne(Integer id);

}
