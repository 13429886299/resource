package com.zmlh.until;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @Interface RootMapper
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-20 15:34
 * @Version 1.0
 **/
public interface RootMapper<T> extends BaseMapper<T> {
    /**
     * 自定义批量插入
     * 如果要自动填充，@Param(xx) xx参数名必须是 list/collection/array 3个的其中之一
     */
    int insertBatch ( @Param("list") List<T> list );

    /**
     * 自定义批量更新，条件为主键
     * 如果要自动填充，@Param(xx) xx参数名必须是 list/collection/array 3个的其中之一
     */
    int updateBatchByKey ( @Param("list") Collection<T> entityList );

    /**
     * 自定义批量更新，条件为主键
     * 如果要自动填充，@Param(xx) xx参数名必须是 list/collection/array 3个的其中之一
     */
    int updateBatch ( @Param("list") List<T> list, @Param(Constants.WRAPPER) Wrapper<T> updateWrapper );
}
