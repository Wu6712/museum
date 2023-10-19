package com.yy.museum.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    @ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty(value = "主键")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty(value = "账号")
      private String username;

      @ApiModelProperty(value = "密码")
      @JsonIgnore
      private String password;

      @ApiModelProperty(value = "是否在线")
      private Integer online;

      @ApiModelProperty(value = "昵称")
      private String nickname;

      @ApiModelProperty(value = "性别")
      private Integer sex;

      @ApiModelProperty(value = "地址")
      private String address;

      @ApiModelProperty(value = "信用分")
      private String score;


}
