package com.baidu.ssp.querys;

import com.baidu.ssp.tables.Table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mojie on 2015/2/9.
 */
public class MetricQuery implements Query {

    private Table table;

    private List<String> dimensions;

    private List<String> metrics;

    private TimeGranularity timeGranularity;

    private String timeField;

    private List<ValueFilter> filters;

    private Date begin;

    private Date end;

    public MetricQuery(Table table, List<String> dimensions, List<String> metrics,
                       TimeGranularity timeGranularity, String timeField,
                       Date begin, Date end) {
        this.table = table;
        this.dimensions = dimensions;
        this.metrics = metrics;
        this.timeGranularity = timeGranularity;
        this.timeField = timeField;
        this.begin = begin;
        this.end = end;
        this.filters = new ArrayList<ValueFilter>();
    }

    public Table getTable() {
        return table;
    }

    public List<String> getDimensions() {
        return dimensions;
    }

    public List<String> getMetrics() {
        return metrics;
    }

    public TimeGranularity getTimeGranularity() {
        return timeGranularity;
    }

    public String getTimeField() {
        return timeField;
    }

    public void addFilter(ValueFilter valueFilter) {
        this.filters.add(valueFilter);
    }

    public void addFilters(List<ValueFilter> filters) {
        this.filters.addAll(filters);
    }

    public List<ValueFilter> getFilters() {
        return filters;
    }

    public Date getBegin() {
        return begin;
    }

    public Date getEnd() {
        return end;
    }
}
