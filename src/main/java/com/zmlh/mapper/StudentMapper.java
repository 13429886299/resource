package com.zmlh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zmlh.entity.StudentInfoTab;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Interface StudentMapper
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-06 17:54
 * @Version 1.0
 **/
@Repository
@Mapper
public interface StudentMapper extends BaseMapper<StudentInfoTab> {
}
