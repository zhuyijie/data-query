package com.baidu.ssp.executors.impl;

import com.baidu.ssp.querys.MetricQuery;
import com.baidu.ssp.querys.TimeGranularity;
import com.baidu.ssp.querys.ValueFilter;
import com.baidu.ssp.tables.Field;
import com.baidu.ssp.tables.Table;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by mojie on 2015/2/10.
 */
public class MetricQueryExecutor extends AbstractExecutor<MetricQuery> {


    private final static String BY_HOUR_SQL = "(hour(from_unixtime(%1$s))) as %2$s";
    private final static String BY_DAY_SQL = "(year(from_unixtime(%1$s))*10000 " +
            "+ month(from_unixtime(%1$s))* 100 " +
            "+ day(from_unixtime(%1$s))) as %2$s";
    /**
     * unix时间0秒时，中国为早上8点，星期四
     * 按照中国习惯，星期一到星期日为一周
     * 这个SQL算的是unix时间0秒开始的周数，第一周为0
     * */
    private final static String BY_WEEK_SQL = "((((%1$s + (24 * 3600 - unix_timestamp('1970-01-02')))) div (3600 * 24) + 3) div 7) as %2$s";

    private final static String BY_MONTH_SQL = "(year(from_unixtime(%1$s))*100 " +
            "+ month(from_unixtime(%1$s))) as %2$s";

    private final static String BY_YEAR_SQL = "(year(from_unixtime(%1$s))) as %2$s";

    private final static Map<TimeGranularity, String> TIME_GRANULARITY_STRING_MAP;

    static {
        Map<TimeGranularity, String> map = Maps.newHashMap();
        map.put(TimeGranularity.HOUR, BY_HOUR_SQL);
        map.put(TimeGranularity.DAY, BY_DAY_SQL);
        map.put(TimeGranularity.WEEK, BY_WEEK_SQL);
        map.put(TimeGranularity.MONTH, BY_MONTH_SQL);
        map.put(TimeGranularity.YEAR, BY_YEAR_SQL);

        TIME_GRANULARITY_STRING_MAP = Collections.unmodifiableMap(map);
    }

    /**
     * 生成sql
     *
     * @param query
     */
    @Override
    public String getSql(MetricQuery query) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select ").append(selectClause(query))
                .append(" from ").append(query.getTable().getTableName())
                .append(" where ").append(whereClause(query));
        String groupClause = groupClause(query);
        if (!groupClause.isEmpty()) {
            stringBuilder.append(" group by ").append(groupClause);
        }
        return stringBuilder.toString();
    }

    private static String selectClause(MetricQuery query) {
        Table table = query.getTable();
        List<String> selectParts = new ArrayList<String>();
        // process dimensions
        List<String> dimensions = query.getDimensions();
        for (String dimension : dimensions) {
            selectParts.add(String.format("%s as %s", table.getField(dimension).getRawExpression(), dimension));
        }
        // process metrics
        List<String> metrics = query.getMetrics();
        for (String metric : metrics) {
            selectParts.add(String.format("sum(%s) as %s", table.getField(metric).getRawExpression(), metric));
        }
        // process time field
        TimeGranularity timeGranularity = query.getTimeGranularity();
        String timePartFormat = TIME_GRANULARITY_STRING_MAP.get(timeGranularity);
        String timePart;
        if (timePartFormat != null) {
            Field field = table.getField(query.getTimeField());
            timePart = String.format(timePartFormat, field.getRawExpression(), field.getName());
        } else {
            timePart = String.format("'time_granularity_sum' as %s", query.getTimeField());
        }
        selectParts.add(timePart);
        return Joiner.on(',').join(selectParts);
    }

    private static String whereClause(MetricQuery query) {

        List<String> whereParts = new ArrayList<String>();

        List<ValueFilter> filters = query.getFilters();
        Function<ValueFilter, String> valueFilterStringFunction =
                new ValueFilterFormatFunction(query.getTable());
        for (ValueFilter aFilter : filters) {
            whereParts.add(valueFilterStringFunction.apply(aFilter));
        }
        Field timeField = query.getTable().getField(query.getTimeField());
        Date begin = checkNotNull(query.getBegin());
        Date end = checkNotNull(query.getEnd());
        long beginSeconds = begin.getTime() / 1000;
        long endSeconds = end.getTime() / 1000;
        whereParts.add(String.format("%1$s >= %2$d and %1$s <= %3$d",
                timeField.getRawExpression(), beginSeconds, endSeconds));
        return Joiner.on(" and ").join(whereParts);
    }

    private static String groupClause(MetricQuery query) {
        List<String> dimensions = query.getDimensions();
        List<String> groupByParts = new ArrayList<String>(dimensions);
        groupByParts.add(query.getTimeField());
        return Joiner.on(',').join(groupByParts);
    }
}
