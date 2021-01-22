package com.zmlh.mapper;

import com.zmlh.entity.ExcelSchedule;
import com.zmlh.until.RootMapper;
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
public interface ExcelScheduleMapper extends RootMapper<ExcelSchedule> {
}
