package com.zmlh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zmlh.entity.ResRoleTab;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Interface RoleMapper
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-06 11:03
 * @Version 1.0
 **/
@Repository
@Mapper
public interface RoleMapper extends BaseMapper<ResRoleTab> {
}
