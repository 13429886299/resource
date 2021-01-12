package com.zmlh.until;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zmlh.entity.Response;
import com.zmlh.mapper.ResNodeTabMapper;
import com.zmlh.mapper.StudentMapper;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.*;

/**
 * @ClassName BaseDatabaseInterface
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-06 11:27
 * @Version 1.0
 **/

public interface BaseDatabaseInterface<T> extends DataBaseCommonInterface<T> {
    String ID = "id";
    String SET = "set";
    String GET = "get";

    default String creatId () {
        return UUID.randomUUID().toString().substring(0, 32).replace("-", "");
    }

    default Response insert ( BaseMapper<T> mapper, T t, Serializable serializable ) {
        if (mapper.selectById(serializable) == null) {
            Field[] fields = t.getClass().getDeclaredFields();
            String id = creatId();
            Arrays.asList(fields).forEach(field -> {
                String setMethods = SET + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
                try {
                    Method method = t.getClass().getMethod(setMethods, field.getType());
                    if (ID.equals(field.getName())) {
                        method.invoke(t, id);
                    }
                    if (field.getType().isAssignableFrom(Instant.class)) {
                        method.invoke(t, Instant.now());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            mapper.insert(t);
            return new Response().setCode(200).setObject(id);
        }
        return update(t);
    }

    default <S> Map<String, S> listToMap ( List<S> studentInfoTabList, String keyFile ) {
        Map<String, S> map = new HashMap<>(16);
        String setMethods = GET + Character.toUpperCase(keyFile.charAt(0)) + keyFile.substring(1);
        studentInfoTabList.forEach(s -> {
            try {
                String key = s.getClass().getMethod(setMethods).invoke(s).toString();
                map.put(key, s);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
        return map;
    }

    @SneakyThrows
    default <S> S getBean ( Map<String, S> sMap, String key, Class<S> sClass ) {
        S s = sMap.get(key);
        return s == null ? sClass.newInstance() : s;
    }
}
