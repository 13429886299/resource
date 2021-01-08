package com.zmlh.server;

import com.zmlh.entity.LoginInfo;
import com.zmlh.entity.Response;

/**
 * @Interface LoginServer
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-07 10:39
 * @Version 1.0
 **/
public interface LoginServer   {

    Response login ( LoginInfo loginInfo );
}
