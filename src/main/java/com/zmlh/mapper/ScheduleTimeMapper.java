package com.zmlh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zmlh.entity.ScheduleTimeTab;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Interface ScheduleTimeMapper
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-13 15:23
 * @Version 1.0
 **/
@Repository
@Mapper
public interface ScheduleTimeMapper extends BaseMapper<ScheduleTimeTab> {
}
