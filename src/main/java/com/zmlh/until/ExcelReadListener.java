package com.zmlh.until;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zmlh.entity.ScheduleTimeTab;
import com.zmlh.mapper.ScheduleTimeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName ExcelReadListener
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-14 16:38
 * @Version 1.0
 **/
@Component
@Slf4j
public class ExcelReadListener extends AnalysisEventListener<Map<String, String>> {
    @Autowired
    private ScheduleTimeMapper scheduleTimeMapper;
    private String season;

    public ExcelReadListener () {
    }

    public ExcelReadListener ( String season ) {
        this.season = season;
    }


    /**
     * 存储读取到的表头
     */
    private List<String> head = new ArrayList<>();
    /**
     * 存储读取到的 Excel 数据
     */
    private List<List<String>> data = new ArrayList<>();

    /**
     * 每解析一行都会回调invoke()方法
     *
     * @param item    读取后的数据对象
     * @param context 内容
     */
    @Override
    public void invoke ( Map<String, String> item, AnalysisContext context ) {
        if (item != null && !item.isEmpty()) {
            List<String> info = new ArrayList<>(item.values());
            data.add(info);
        }
    }

    @Override
    public void doAfterAllAnalysed ( AnalysisContext context ) {
        log.info("当前季节是:" + season);
        List<ScheduleTimeTab> scheduleTimeTabList = scheduleTimeMapper.selectList(new QueryWrapper<ScheduleTimeTab>().eq("season", season));
        if(Objects.nonNull(head)){

        }
    }

    /**
     * 处理读取到的表头数据
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap ( Map<Integer, String> headMap, AnalysisContext context ) {
        if (headMap != null && !headMap.isEmpty()) {
            head = new ArrayList<>(headMap.values());
        }
    }

    /**
     * 获取表头数据信息
     *
     * @return
     */
    public List<String> getHead () {
        return this.head;
    }

    /**
     * 获取读取到的 Excel 数据
     *
     * @return
     */
    public List<List<String>> getData () {
        return this.data;
    }


}
