package com.zmlh.resource;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.fastjson.JSON;
import com.zmlh.until.ExcelReadListener;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@SpringBootTest
class ApplicationTests {


    @Test
    @SneakyThrows
    void contextLoads () {
        String path = "C:\\Users\\7\\Desktop\\逐梦轮滑课程安排表.xlsx";
        InputStream inputStream = new FileInputStream(path);
        ExcelReadListener excelReadListener = new ExcelReadListener();
        ExcelReader reader = EasyExcelFactory.read(inputStream, excelReadListener).build();
        // 读取Sheet,从第0行开始读取(表示从表头开始读)
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        reader.read(readSheet);
        reader.finish();
        List<String> head = excelReadListener.getHead();
        System.out.println(JSON.toJSONString(head));
        List<List<String>> data = excelReadListener.getData();
        System.out.println(JSON.toJSONString(data));
    }

}
