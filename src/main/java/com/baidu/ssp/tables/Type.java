package com.baidu.ssp.tables;

import com.baidu.ssp.type.Converters;
import com.google.common.base.Function;
import com.google.common.collect.BiMap;
import com.google.common.collect.Collections2;
import com.google.common.collect.HashBiMap;

import java.util.Collection;
import java.util.HashMap;

/**
 * 报表数据类型枚举类
 * @author zhuyijie
 * @date 14-12-1
 */
public enum Type {

    INTEGER,
    LONG,
    DOUBLE,
    STRING,
    BOOLEAN;

    private static final BiMap<Type, Class> TYPE_MAP = HashBiMap.create(new HashMap<Type, Class>() {
        {
            put(INTEGER, Integer.class);
            put(LONG, Long.class);
            put(DOUBLE, Double.class);
            put(STRING, String.class);
            put(BOOLEAN, Boolean.class);
        }
    });



    /**
     *
     * 获取实际类型
     *
     * */
    public Class getType() {
        return TYPE_MAP.get(this);
    }

    /**
     * 根据class 类型获取枚举值
     *
     * */
    public static Type valueOf(Class type) {
        return TYPE_MAP.inverse().get(type);
    }
    /**
     *
     * 将数据类型转化成本类型
     *
     * */
    public Object newValue(Object value) {
        return Converters.convert(value, getType());
    }

    public Object newValueWithDefaultValue(Object value) {
        if (value == null) {
            switch (this) {
                case BOOLEAN:
                    return false;
                case INTEGER:
                    return 0;
                case LONG:
                    return 0L;
                case DOUBLE:
                    return 0d;
                default:
                    return null;
            }
        } else {
            return newValue(value);
        }
    }

    public Collection transValues(Collection values) {
        return Collections2.transform(values, new Function() {
            @Override
            public Object apply(Object input) {
                return newValue(input);
            }
        });
    }
}
