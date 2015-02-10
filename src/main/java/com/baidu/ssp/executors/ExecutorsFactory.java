package com.baidu.ssp.executors;

import com.baidu.ssp.executors.impl.DistinctQueryExecutor;
import com.baidu.ssp.executors.impl.MetricQueryExecutor;
import com.baidu.ssp.executors.impl.SingletonProvider;
import com.baidu.ssp.querys.DistinctQuery;
import com.baidu.ssp.querys.MetricQuery;
import com.baidu.ssp.querys.Query;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mojie on 2015/2/9.
 */
public class ExecutorsFactory {

    private static final Map<Class<? extends Query>, ExecutorProvider> EXECUTOR_TABLE =
            new HashMap<Class<? extends Query>, ExecutorProvider>();

    static {
        register(MetricQuery.class, new SingletonProvider(new MetricQueryExecutor()));
        register(DistinctQuery.class, new SingletonProvider(new DistinctQueryExecutor()));
    }

    public static void register(Class<? extends Query> queryType, ExecutorProvider provider) {
        EXECUTOR_TABLE.put(queryType, provider);
    }

    public static <T extends Query> Executor<T> lookupExecutor(T query) {
        return EXECUTOR_TABLE.get(query.getClass()).getExecutor();
    }
}
