package com.zmlh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zmlh.entity.DictAllInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @ClassName DictAllInfoMapper
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-05 17:57
 * @Version 1.0
 **/
@Repository
@Mapper
public interface DictAllInfoMapper extends BaseMapper<DictAllInfo> {
    @Delete("delete dict_all_value_tab where id = #{id}")
    void deleteById ( String id );
}
