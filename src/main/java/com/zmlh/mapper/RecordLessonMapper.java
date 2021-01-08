package com.zmlh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zmlh.entity.ResRecordLessonTab;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Interface RecordLessonMapper
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-08 14:49
 * @Version 1.0
 **/
@Repository
@Mapper
public interface RecordLessonMapper extends BaseMapper<ResRecordLessonTab> {
}
