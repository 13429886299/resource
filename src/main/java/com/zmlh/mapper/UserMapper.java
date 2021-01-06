package com.zmlh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zmlh.entity.ResUserTab;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Interface UserMapper
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-06 16:51
 * @Version 1.0
 **/
@Repository
@Mapper
public interface UserMapper extends BaseMapper<ResUserTab> {
}
