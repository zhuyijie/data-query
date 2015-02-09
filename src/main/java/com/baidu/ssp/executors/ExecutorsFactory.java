package com.baidu.ssp.executors;

import com.baidu.ssp.querys.Query;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mojie on 2015/2/9.
 */
public class ExecutorsFactory {

    private static final Map<Class<? extends Query>, ExecutorProvider> EXECUTOR_TABLE =
            new HashMap<Class<? extends Query>, ExecutorProvider>();

    public static void register(Class<? extends Query> queryType, ExecutorProvider provider) {
        EXECUTOR_TABLE.put(queryType, provider);
    }

    public static Executor lookupExecutor(Query query) {
        return EXECUTOR_TABLE.get(query.getClass()).getExecutor();
    }
}
