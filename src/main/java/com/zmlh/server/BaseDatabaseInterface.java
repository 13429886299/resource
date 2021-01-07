package com.zmlh.server;

import com.zmlh.entity.Response;

import java.util.UUID;

/**
 * @ClassName BaseDatabaseInterface
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-06 11:27
 * @Version 1.0
 **/
public interface BaseDatabaseInterface<T> {

    Response getAll ();

    Response getAllById ( String id );

    Response insert ( T t );

    Response update ( T t );

    Response delete ( String id );

    default String creatId () {
        return UUID.randomUUID().toString().substring(0, 63);
    }
}
