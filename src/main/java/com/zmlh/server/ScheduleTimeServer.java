package com.zmlh.server;

import com.zmlh.entity.Response;
import com.zmlh.entity.ScheduleTimeTab;
import com.zmlh.until.BaseDatabaseInterface;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/**
 * @Interface ScheduleTimeServer
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-13 15:24
 * @Version 1.0
 **/
public interface ScheduleTimeServer extends BaseDatabaseInterface<ScheduleTimeTab> {
    Response insertExcel ( MultipartFile file, String season, Instant time );

    void getModelExcel ( long season, HttpServletResponse response );

    Response getPage ( int pageNo, int pageSize,Instant time );
}
