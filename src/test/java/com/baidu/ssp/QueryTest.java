package com.baidu.ssp;

import com.baidu.ssp.element.ListFrame;
import com.baidu.ssp.executors.Executor;
import com.baidu.ssp.executors.ExecutorsFactory;
import com.baidu.ssp.querys.DistinctQuery;
import com.baidu.ssp.querys.MetricQuery;
import com.baidu.ssp.querys.TimeGranularity;
import com.baidu.ssp.querys.ValueFilter;
import com.baidu.ssp.tables.Table;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mojie on 2015/2/10.
 */
public class QueryTest {


    @Test
    public void testDayQuery() throws SQLException, ParseException {
        Table table = TableFactory.defaultTable;
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        MetricQuery metricQuery = new MetricQuery(table,
                Arrays.asList("adPositionId"),
                Arrays.asList("view"),
                TimeGranularity.DAY,
                "timeField",
                dateFormat.parse("20141201000000"),
                dateFormat.parse("20141205235959"));
        metricQuery.addFilter(new ValueFilter<Long>("adPositionId", 1001L));
        Executor<MetricQuery> executor = ExecutorsFactory.lookupExecutor(metricQuery);
        Frame result = executor.execute(metricQuery);
        Assert.assertEquals(result, makeFrame(
                new String[] {"timeField", "adPositionId", "view"},
                new Object[][] {
                        {"20141201", 1001L, 100L},
                        {"20141202", 1001L, 100L},
                        {"20141203", 1001L, 100L},
                        {"20141204", 1001L, 100L},
                        {"20141205", 1001L, 100L},
                }
        ));
    }

    @Test
    public void testDistinct() {
        Table table = TableFactory.defaultTable;
        DistinctQuery distinctQuery = new DistinctQuery(table,
                Arrays.asList("adPositionId", "userId"));
        distinctQuery.addFilter(new ValueFilter<Long>("adPositionId", Arrays.asList(1001L, 1003L)));
        Executor<DistinctQuery> executor = ExecutorsFactory.lookupExecutor(distinctQuery);
        Frame result = executor.execute(distinctQuery);
        Assert.assertEquals(result, makeFrame(
                new String[] {"userId", "adPositionId"},
                new Object[][] {
                        {1422795L, 1001L}
                }
        ));

    }

    public static Frame makeFrame(String[] titles, Object[][] data) {
        return ListFrame.newInstance(Lists.newArrayList(Iterables.transform(Arrays.asList(data), new Function<Object[], List<Object>>() {
            @Override
            public List<Object> apply(Object[] input) {
                return Arrays.asList(input);
            }
        })), Arrays.asList(titles));
    }
}
