package com.zmlh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zmlh.entity.ResNodeTab;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Interface ResNodeTabMapper
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-05 20:11
 * @Version 1.0
 **/
@Repository
@Mapper
public interface ResNodeTabMapper extends BaseMapper<ResNodeTab> {
}
