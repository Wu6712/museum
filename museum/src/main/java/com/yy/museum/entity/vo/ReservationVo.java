package com.yy.museum.entity.vo;

import com.yy.museum.entity.Museum;
import com.yy.museum.entity.Reservation;
import com.yy.museum.entity.User;
import lombok.Data;

@Data
public class ReservationVo {

    private Reservation reservation;

    private User user;

    private Museum museum;

}
