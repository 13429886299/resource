package com.zmlh.server;

import com.zmlh.entity.DictAllInfo;
import com.zmlh.entity.DictInfo;
import com.zmlh.entity.Response;

/**
 * @Interface DictServer
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-05 16:57
 * @Version 1.0
 **/
public interface DictServer extends BaseInterface<DictInfo> {
    Response insert ( DictAllInfo dictAllInfo );

    Response update ( DictAllInfo dictAllInfo );

    Response deleteBig ( String id );

    Response deleteSmall ( String id );
}
