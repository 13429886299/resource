package com.zmlh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zmlh.entity.DictInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @ClassName DictInfoMapper
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-05 17:47
 * @Version 1.0
 **/
@Repository
@Mapper
public interface DictInfoMapper extends BaseMapper<DictInfo> {
}
