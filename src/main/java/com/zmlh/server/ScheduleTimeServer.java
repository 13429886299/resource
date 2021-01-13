package com.zmlh.server;

import com.zmlh.entity.Response;
import com.zmlh.entity.ScheduleTimeTab;
import com.zmlh.until.BaseDatabaseInterface;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Interface ScheduleTimeServer
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-13 15:24
 * @Version 1.0
 **/
public interface ScheduleTimeServer extends BaseDatabaseInterface<ScheduleTimeTab> {
    Response insertExcel ( MultipartFile file, String season );

    void getModelExcel ( String season, HttpServletResponse response );
}
