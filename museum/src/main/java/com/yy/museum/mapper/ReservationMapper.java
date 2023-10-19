package com.yy.museum.mapper;

import com.yy.museum.entity.Reservation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yy.museum.entity.vo.ReservationVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yao
 * @since 2023-08-16
 */
@Mapper
public interface ReservationMapper extends BaseMapper<Reservation> {

    List<Reservation> getList(@Param("pageNum") Integer pageNum,@Param("pageSize") Integer pageSize);
    @Select("select count(*) from reservation")
    Integer total();

    @Insert("insert into reservation values(null, #{userId}, #{museumId}, #{status}, #{startTime}, #{endTime})")
    int saveOne(Reservation reservation);

    @Select("select count(*) from reservation")
    int getTotal();

    @Select("select * from reservation where user_id = #{userId}")
    List<Reservation> getListByUserId(Integer userId);

    @Update("update reservation set status = 2 where id = #{id}")
    int updateStatusById(Integer id);

    @Update("update reservation set status = 3 where id = #{id}")
    int sign(Integer id);

    @Update("update reservation set status = 4 where id = #{id}")
    int updateOne(Integer id);

}
