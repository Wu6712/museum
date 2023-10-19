package com.yy.museum.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yy.museum.entity.Museum;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yao
 * @since 2023-08-16
 */
public interface IMuseumService extends IService<Museum> {

    IPage<Museum> findList(Integer pageNum, Integer pageSize);
}
