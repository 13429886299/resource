package com.zmlh.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zmlh.dictionary.ExcelDictionary;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName ExcelFile
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-13 19:17
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
public class ExcelFile {
    @ExcelProperty(value = ExcelDictionary.TIME, index = 0)
    private String title;
    @ExcelProperty(index = 1)
    private String time0;
    @ExcelProperty(index = 2)
    private String time1;
    @ExcelProperty(index = 3)
    private String time2;
    @ExcelProperty(index = 4)
    private String time3;
}
