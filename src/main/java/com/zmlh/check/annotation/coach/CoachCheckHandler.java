package com.zmlh.check.annotation.coach;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zmlh.entity.ResUserTab;
import com.zmlh.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * @ClassName CoachCheckHandler
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-11 18:52
 * @Version 1.0
 **/
@Component
public class CoachCheckHandler implements ConstraintValidator<CoachCheck, String> {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean isValid ( String str, ConstraintValidatorContext constraintValidatorContext ) {
        List<ResUserTab> userList = userMapper.selectList(new QueryWrapper<>());
        return userList.stream().anyMatch(resUserTab -> resUserTab.getId().equals(str));
    }
}
