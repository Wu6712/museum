package com.yy.museum.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yy.museum.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yao
 * @since 2023-08-16
 */
public interface INoticeService extends IService<Notice> {

    IPage<Notice> findList(Integer pageNum, Integer pageSize);
}
