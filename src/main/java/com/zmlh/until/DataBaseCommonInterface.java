package com.zmlh.until;

import com.zmlh.entity.Response;

/**
 * @Interface DataBaseCommonInterface
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-08 9:44
 * @Version 1.0
 **/
public interface DataBaseCommonInterface<T> {
    Response getAll ();

    Response getAllById ( String id );

    Response insert ( T t );

    Response update ( T t );

    Response delete ( String id );
}
