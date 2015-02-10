package com.baidu.ssp.executors.impl;

import com.baidu.ssp.querys.ValueFilter;
import com.baidu.ssp.tables.Field;
import com.baidu.ssp.tables.Table;
import com.google.common.base.Function;
import com.google.common.base.Joiner;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by yijiezhu on 15-2-10.
 */
public class ValueFilterFormatFunction implements Function<ValueFilter, String> {

    private Table table;

    public ValueFilterFormatFunction(Table table) {
        this.table = table;
    }

    @Override
    public String apply(ValueFilter aFilter) {
        Collection values = aFilter.getValues();
        Field filterField = table.getField(aFilter.getField());
        checkState(!values.isEmpty(), "filter values are empty.");
        if (values.size() == 1) {
            return String.format("%s = %s",
                    filterField.getRawExpression(), values.iterator().next());
        } else {
            return String.format("%s in (%s)",
                    filterField.getRawExpression(), Joiner.on(',').join(values));
        }
    }
}
