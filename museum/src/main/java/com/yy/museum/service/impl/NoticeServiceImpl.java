package com.yy.museum.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yy.museum.entity.Notice;
import com.yy.museum.mapper.NoticeMapper;
import com.yy.museum.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yao
 * @since 2023-08-16
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

    @Override
    public IPage<Notice> findList(Integer pageNum, Integer pageSize) {
        return this.page(new Page<>(pageNum,pageSize));
    }
}
