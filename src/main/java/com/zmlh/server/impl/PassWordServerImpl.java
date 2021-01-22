package com.zmlh.server.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zmlh.dictionary.LoginDictionary;
import com.zmlh.entity.LoginInfo;
import com.zmlh.entity.Pwd;
import com.zmlh.entity.ResUserTab;
import com.zmlh.entity.Response;
import com.zmlh.mapper.LoginMapper;
import com.zmlh.mapper.UserMapper;
import com.zmlh.server.PassWordServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @ClassName PassWordServerImpl
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-12 15:41
 * @Version 1.0
 **/
@Service
@Slf4j
public class PassWordServerImpl implements PassWordServer {
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Response update ( Pwd pwd ) {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name", pwd.getUser());
        if (LoginDictionary.ADMIN.equals(pwd.getUser().toLowerCase())) {
            return new Response()
                    .setCode(200)
                    .setObject(loginMapper.update(new LoginInfo().setPwd(pwd.getPwd()), updateWrapper));

        } else {
            return new Response()
                    .setObject(userMapper.update(new ResUserTab().setPwd(pwd.getPwd()), updateWrapper))
                    .setCode(200);
        }
    }

    @Override
    public Response restPwd ( String userId ) {
        String md5Str = DigestUtils.md5DigestAsHex(LoginDictionary.DEFAULTPWD.getBytes());
        return update(new Pwd().setPwd(md5Str).setUser(userId));
    }
}
