package com.baidu.ssp.querys;

import com.baidu.ssp.tables.Table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by mojie on 2015/2/10.
 */
public class DistinctQuery implements Query {

    private Table table;

    private List<String> fields;

    private List<ValueFilter> filters;

    public DistinctQuery(Table table, List<String> fields) {
        this.table = table;
        this.fields = fields;
        this.filters = new ArrayList<ValueFilter>();
    }

    public void addFilter(ValueFilter valueFilter) {
        this.filters.add(valueFilter);
    }
    public void addFilters(Collection<ValueFilter> filters) {
        this.filters.addAll(filters);
    }

    public List<ValueFilter> getFilters() {
        return filters;
    }

    public Table getTable() {
        return table;
    }

    public List<String> getFields() {
        return fields;
    }
}
