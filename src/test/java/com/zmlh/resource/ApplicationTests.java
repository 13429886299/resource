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
import java.time.*;
import java.util.List;

//@SpringBootTest
class ApplicationTests {


    @Test
    @SneakyThrows
    void contextLoads () {
        Instant instant = Instant.now();
        System.out.println(instant.toString());
    }

}
