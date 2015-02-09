package com.baidu.ssp.executors;

import com.baidu.ssp.Frame;
import com.baidu.ssp.querys.Query;

/**
 * Created by mojie on 2015/2/9.
 */
public interface Executor {

    public Frame execute(Query query);

}
