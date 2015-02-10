package com.baidu.ssp.querys;

/**
 * Created by zhuyijie on 14-6-15.
 */
public enum TimeGranularity {
    HOUR, DAY, WEEK, MONTH, YEAR, SUM;

    public int getValue() {
        return this.ordinal();
    }

    public static TimeGranularity valueOf(int value) {
        return TimeGranularity.values()[value];
    }

    public boolean ge(TimeGranularity other) {
        return this.getValue() >= other.getValue();
    }
}
