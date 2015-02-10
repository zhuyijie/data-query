package com.baidu.ssp.executors.impl;

import com.baidu.ssp.executors.Executor;
import com.baidu.ssp.executors.ExecutorProvider;
import com.baidu.ssp.querys.Query;

/**
 * Created by mojie on 2015/2/10.
 */
public class SingletonProvider<T extends Query> implements ExecutorProvider<T> {

    private Executor<T> executor;

    public SingletonProvider(Executor<T> executor) {
        this.executor = executor;
    }

    @Override
    public Executor<T> getExecutor() {
        return executor;
    }
}
