package com.zmlh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zmlh.entity.LoginInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Interface LoginMapper
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-07 11:22
 * @Version 1.0
 **/
@Repository
@Mapper
public interface LoginMapper extends BaseMapper<LoginInfo> {

    @Select("select LoginInfo from dict_login_tab where username = #{userName}")
    LoginInfo getLoginInfo ( String userName );
}
