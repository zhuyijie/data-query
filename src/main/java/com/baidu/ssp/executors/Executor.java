package com.baidu.ssp.executors;

import com.baidu.ssp.Frame;
import com.baidu.ssp.querys.Query;

/**
 * Created by mojie on 2015/2/9.
 */
public interface Executor<T extends Query> {

    /**
     *
     * 执行查询，获取结果
     *
     * */
    public Frame execute(T query);

}
