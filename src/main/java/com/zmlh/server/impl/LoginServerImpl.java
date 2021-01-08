package com.zmlh.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zmlh.dictionary.LoginDictionary;
import com.zmlh.entity.LoginInfo;
import com.zmlh.entity.ResUserTab;
import com.zmlh.entity.Response;
import com.zmlh.mapper.LoginMapper;
import com.zmlh.mapper.UserMapper;
import com.zmlh.myexception.LoginException;
import com.zmlh.until.AbstractExceptionHandler;
import com.zmlh.server.LoginServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


import java.util.List;
import java.util.Objects;

/**
 * @ClassName LoginServerImpl
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-07 10:39
 * @Version 1.0
 **/
@Service
@Slf4j
@SuppressWarnings("all")
public class LoginServerImpl extends AbstractExceptionHandler implements LoginServer {
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private UserMapper userMapper;

    @Override

    public Response login ( LoginInfo loginInfo ) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        String md5Str = DigestUtils.md5DigestAsHex(loginInfo.getPwd().getBytes());
        log.info("MD5加密的数据是：" + md5Str);
        queryWrapper.eq("name", loginInfo.getUser());
        List<LoginInfo> loginInfoList = loginMapper.selectList(queryWrapper);
        if (loginInfoList.size() == 1) {
            if (md5Str.equals(loginInfoList.get(0).getPwd())) {
                loginInfo.setUserName(LoginDictionary.LOGIN_ADMIN).setRoleId(0);
                return new Response().setCode(200).setObject(loginInfo);
            } else {
                exceptionHandler(LoginDictionary.LOGIN_PWD_ERR, LoginException.class);
                return null;
            }
        }
        List<ResUserTab> userTabList = userMapper.selectList(queryWrapper);
        if (userTabList.size() == 1) {
            ResUserTab resUserTab = userTabList.get(0);
            if (md5Str.equals(resUserTab.getPwd())) {
                loginInfo.setUserName(resUserTab.getUserName()).setRoleId(resUserTab.getRoleId());
                return new Response().setCode(200).setObject(loginInfo);
            } else {
                exceptionHandler(LoginDictionary.LOGIN_PWD_ERR, LoginException.class);
                return null;
            }
        }
        check(userTabList);
        check(loginInfoList);
        return null;
    }

    private void check ( List list ) {
        if (Objects.nonNull(list) && list.size() == 0) {
            exceptionHandler(LoginDictionary.LOGIN_NO_USER, LoginException.class);
        }
        if (Objects.nonNull(list) && list.size() > 1) {
            exceptionHandler(LoginDictionary.LOGIN_FAIL, LoginException.class);
        }
    }
}
