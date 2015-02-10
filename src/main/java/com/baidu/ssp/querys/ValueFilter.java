package com.baidu.ssp.querys;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by mojie on 2015/2/9.
 */
public class ValueFilter<T> {

    private String field;

    private Collection<T> values;


    public ValueFilter(String field, Collection<T> values) {
        this.field = field;
        this.values = values;
    }

    public ValueFilter(String field, T value) {
        this.field = field;
        this.values = Arrays.asList(value);
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Collection<T> getValues() {
        return values;
    }

    public void setValues(Collection<T> values) {
        this.values = values;
    }
}
