package com.yy.museum.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yy.museum.entity.Museum;
import com.yy.museum.mapper.MuseumMapper;
import com.yy.museum.service.IMuseumService;
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
public class MuseumServiceImpl extends ServiceImpl<MuseumMapper, Museum> implements IMuseumService {

    @Override
    public IPage<Museum> findList(Integer pageNum, Integer pageSize) {
        return this.page(new Page<>(pageNum,pageSize));
    }
}
