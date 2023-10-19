package com.yy.museum.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 *
 * </p>
 *
 * @author yao
 * @since 2023-08-16
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @ApiModel(value="Reservation对象", description="")
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private Integer userId;

    private Integer museumId;

      @ApiModelProperty(value = "0，预约，1，取消，2，开始，3，完成")
      private Integer status;

//    @JsonFormat(pattern = "yyyy:MM:dd HH:mm")
    @DateTimeFormat(pattern = "yyyy:MM:dd HH:mm")
    private LocalDateTime startTime;

//    @JsonFormat(pattern = "yyyy:MM:dd HH:mm")
    @DateTimeFormat(pattern = "yyyy:MM:dd HH:mm")
    private LocalDateTime endTime;

}
