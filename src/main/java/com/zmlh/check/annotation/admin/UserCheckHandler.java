package com.zmlh.check.annotation.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zmlh.dictionary.LoginDictionary;
import com.zmlh.entity.LoginInfo;
import com.zmlh.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * @ClassName UserCheckHandler
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-12 11:41
 * @Version 1.0
 **/
@Component
public class UserCheckHandler implements ConstraintValidator<UserCheck, String> {
    @Autowired
    private LoginMapper loginMapper;

    @Override
    public boolean isValid ( String str, ConstraintValidatorContext constraintValidatorContext ) {
        List<LoginInfo> loginList = loginMapper.selectList(new QueryWrapper<>());
        return loginList.stream()
                .filter(loginInfo -> loginInfo.getId().equals(str))
                .findAny()
                .orElse(new LoginInfo().setUserName("error"))
                .getUserName()
                .toLowerCase()
                .equals(LoginDictionary.ADMIN);
    }
}
