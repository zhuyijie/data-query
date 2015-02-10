package com.baidu.ssp.executors;

import com.baidu.ssp.querys.Query;

/**
 * Created by mojie on 2015/2/9.
 */
public interface ExecutorProvider<T extends Query> {
    Executor<T> getExecutor();
}
