package com.zmlh.until;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * @ClassName UpdateBatchMethod
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-20 19:20
 * @Version 1.0
 **/
@Slf4j
public class UpdateBatchMethod extends AbstractMethod {
    /**
     * 注入自定义 MappedStatement
     *
     * @param mapperClass mapper 接口
     * @param modelClass  mapper 泛型
     * @param tableInfo   数据库表反射信息
     * @return MappedStatement
     */
    @Override
    public MappedStatement injectMappedStatement ( Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo ) {
        String sql = "<script>\n<foreach collection=\"list\" item=\"item\" separator=\";\">\nupdate %s %s where %s %s\n</foreach>\n</script>";
        String setSql = sqlSet(tableInfo.isLogicDelete(), false, tableInfo, false, "item", "item.");
        String additional = tableInfo.isWithVersion() ? tableInfo.getVersionFieldInfo().getVersionOli("item", "item.") : "" + tableInfo.getLogicDeleteSql(true, true);
        String sqlResult = String.format(sql, tableInfo.getTableName(), setSql,
                sqlWhereEntityWrapper(true, tableInfo), additional);
        log.info("sqlResult----->{}", sqlResult);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sqlResult, modelClass);
        // 第三个参数必须和RootMapper的自定义方法名一致
        return this.addUpdateMappedStatement(mapperClass, modelClass, "updateBatch", sqlSource);
    }
}
