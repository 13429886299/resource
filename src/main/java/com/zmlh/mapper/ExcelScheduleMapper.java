package com.zmlh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zmlh.entity.ExcelSchedule;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Interface ExcelScheduleMapper
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-15 15:48
 * @Version 1.0
 **/
@Repository
@Mapper
public interface ExcelScheduleMapper extends BaseMapper<ExcelSchedule> {
}
