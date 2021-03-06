package com.zmlh.until;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zmlh.entity.ScheduleTimeTab;
import com.zmlh.entity.StudentInfoTab;
import com.zmlh.mapper.ScheduleTimeMapper;
import com.zmlh.mapper.StudentMapper;
import com.zmlh.myexception.ExcelAnalysisEventListenerException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @ClassName ExcelReadListener
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-14 16:38
 * @Version 1.0
 **/
@Component
@Slf4j
public class ExcelReadListener extends AnalysisEventListener<Map<String, String>> implements AbstractExceptionHandler<ExcelAnalysisEventListenerException> {

    private List<ScheduleTimeTab> scheduleTimeTabList;

    private List<StudentInfoTab> studentList;


    public ExcelReadListener () {
    }

    public ExcelReadListener ( List<ScheduleTimeTab> scheduleTimeTabList, List<StudentInfoTab> studentList ) {
        this.scheduleTimeTabList = scheduleTimeTabList;
        this.studentList = studentList;
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
     * 读取每一列后根据head组装数据
     */

    private Map<String, List<String>> map = new HashMap<>(16);

    /**
     * 每解析一行都会回调invoke()方法
     *
     * @param item    读取后的数据对象
     * @param context 内容
     */
    @Override
    public void invoke ( Map<String, String> item, AnalysisContext context ) {
        if (item != null && !item.isEmpty()) {
            data.add(new ArrayList<>(item.values()));
        }
    }

    @Override
    public void doAfterAllAnalysed ( AnalysisContext context ) {
        if (Objects.nonNull(head)) {
            scheduleTimeTabList.forEach(scheduleTimeTab -> {
                if (!head.contains(scheduleTimeTab.getSchedule())) {
                    exceptionHandler("课表选择的季节不对", ExcelAnalysisEventListenerException.class);
                    return;
                }
            });
        }
        if (Objects.nonNull(data)) {
            data.forEach(list -> {
                int raw = data.indexOf(list), length = data.size() - 1;
                for (int i = 1, l = list.size(); i < l; i++) {
                    String s = list.get(i);
                    if (i > 0 && raw < length) {
                        if (studentList.stream().noneMatch(student -> student.getUserName().equals(s))) {
                            exceptionHandler("第" + (raw + 1) + "行，第" + (i + 1) + "列的' " + s + " '不是我们的学员，请核对后上传", ExcelAnalysisEventListenerException.class);
                            return;
                        }
                    }
                    setMapValue(head.get(i), s);
                }
            });
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

    /**
     * 获取读取到的 Excel 整体的数据
     *
     * @return
     */
    public Map<String, List<String>> getMapList () {
        return map;
    }

    private void setMapValue ( String key, String value ) {
        if (map.keySet().contains(key)) {
            map.get(key).add(value);
        } else {
            List<String> list = new ArrayList<>();
            list.add(value);
            map.put(key, list);
        }
    }


}
