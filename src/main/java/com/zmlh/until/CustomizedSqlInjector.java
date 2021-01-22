package com.zmlh.until;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName CustomizedSqlInjector
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-20 15:16
 * @Version 1.0
 **/
@Component
public class CustomizedSqlInjector extends DefaultSqlInjector {
    /**
     * 如果只需增加方法，保留mybatis plus自带方法，
     * 可以先获取super.getMethodList()，再添加add
     */
    @Override
    public List<AbstractMethod> getMethodList ( Class<?> mapperClass ) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.add(new InsertAbstractMethod());
        methodList.add(new UpdateBatchByKeyMethod());
        methodList.add(new UpdateBatchMethod());
        return methodList;
    }


}
