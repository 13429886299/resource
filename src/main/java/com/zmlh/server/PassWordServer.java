package com.zmlh.server;

import com.zmlh.entity.Pwd;
import com.zmlh.entity.Response;

/**
 * @Interface PassWordServer
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-12 15:40
 * @Version 1.0
 **/
public interface PassWordServer {
    Response update ( Pwd pwd );

    Response restPwd ( String userId );
}
