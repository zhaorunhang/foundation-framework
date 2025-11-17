package com.zrh.framework.core.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @PACKAGE_NAME: com.zrh.framework.core.utils
 * @NAME: ZRH
 * @USER: ZRH13
 * @DATE: 2025/11/12
 * @TIME: 16:32
 * @Description:
 */
public class DateUtil {
    @Getter
    public enum Pattern {
        /**
         * 日期格式: yyyy-MM-dd
         */
        DATE("yyyy-MM-dd"),
        /**
         * 日期时间格式: yyyy-MM-dd HH:mm:ss
         */
        DATETIME("yyyy-MM-dd HH:mm:ss"),
        /**
         * 日期时间格式(无秒): yyyy-MM-dd HH:mm
         */
        DATETIME_SHORT("yyyy-MM-dd HH:mm"),
        /**
         * 紧凑日期格式: yyyyMMdd
         */
        DATE_COMPACT("yyyyMMdd"),
        /**
         * 紧凑日期时间格式: yyyyMMddHHmmss
         */
        DATETIME_COMPACT("yyyyMMddHHmmss"),
        /**
         * ISO 日期时间格式: yyyy-MM-dd'T'HH:mm:ss
         */
        ISO_DATETIME("yyyy-MM-dd'T'HH:mm:ss"),
        /**
         * 中文日期格式: yyyy年MM月dd日
         */
        DATE_CN("yyyy年MM月dd日"),
        /**
         * 中文日期时间格式: yyyy年MM月dd日 HH时mm分ss秒
         */
        DATETIME_CN("yyyy年MM月dd日 HH时mm分ss秒"),
        /**
         * 斜杠日期格式: yyyy/MM/dd
         */
        DATE_SLASH("yyyy/MM/dd"),
        /**
         * 斜杠日期时间格式: yyyy/MM/dd HH:mm:ss
         */
        DATETIME_SLASH("yyyy/MM/dd HH:mm:ss"),
        /**
         * 时间格式: HH:mm:ss
         */
        TIME("HH:mm:ss"),
        /**
         * 时间格式(无秒): HH:mm
         */
        TIME_SHORT("HH:mm"),
        /**
         * 年月格式: yyyy-MM
         */
        YEAR_MONTH("yyyy-MM"),
        ;
        private final String pattern;
        private final DateTimeFormatter formatter;

        Pattern(String pattern) {
            this.pattern = pattern;
            this.formatter = DateTimeFormatter.ofPattern(pattern);
        }
    }

    @Getter
    @RequiredArgsConstructor
    public enum TimeUnit {
        /**
         * 年
         */
        YEARS(ChronoUnit.YEARS),
        /**
         * 月
         */
        MONTHS(ChronoUnit.MONTHS),
        /**
         * 周
         */
        WEEKS(ChronoUnit.WEEKS),
        /**
         * 天
         */
        DAYS(ChronoUnit.DAYS),
        /**
         * 小时
         */
        HOURS(ChronoUnit.HOURS),
        /**
         * 分钟
         */
        MINUTES(ChronoUnit.MINUTES),
        /**
         * 秒
         */
        SECONDS(ChronoUnit.SECONDS),
        /**
         * 毫秒
         */
        MILLIS(ChronoUnit.MILLIS),
        ;
        private final ChronoUnit chronoUnit;

    }

    public enum TimeRange {
        /**
         * 今天
         */
        TODAY,
        /**
         * 昨天
         */
        YESTERDAY,
        /**
         * 本周
         */
        THIS_WEEK,
        /**
         * 上周
         */
        LAST_WEEK,
        /**
         * 本月
         */
        THIS_MONTH,
        /**
         * 上月
         */
        LAST_MONTH,
        /**
         * 本季度
         */
        THIS_QUARTER,
        /**
         * 上季度
         */
        LAST_QUARTER,
        /**
         * 今年
         */
        THIS_YEAR,
        /**
         * 去年
         */
        LAST_YEAR,
        /**
         * 最近7天
         */
        RECENT_7_DAYS,
        /**
         * 最近30天
         */
        RECENT_30_DAYS,
        /**
         * 最近90天
         */
        RECENT_90_DAYS,
        /**
         * 最近半年
         */
        RECENT_180_DAYS,
        /**
         * 最近一年
         */
        RECENT_365_DAYS
    }

    @Getter
    @RequiredArgsConstructor
    public enum Weekday {
        /**
         * 周一
         */
        MONDAY(1, "星期一"),
        /**
         * 周二
         */
        TUESDAY(2, "星期二"),
        /**
         * 周三
         */
        WEDNESDAY(3, "星期三"),
        /**
         * 周四
         */
        THURSDAY(4, "星期四"),
        /**
         * 周五
         */
        FRIDAY(5, "星期五"),
        /**
         * 周六
         */
        SATURDAY(6, "星期六"),
        /**
         * 周日
         */
        SUNDAY(7, "星期日"),
        ;
        private final int value;
        private final String cnName;

        public static Weekday getWeekday(int value) {
            for (Weekday weekday : values()) {
                if (weekday.value == value) {
                    return weekday;
                }
            }
            throw new IllegalArgumentException("Invalid weekday value: " + value);
        }

        public boolean isWeekend() {
            return this == SATURDAY || this == SUNDAY;
        }

        public boolean isWeekday() {
            return !isWeekend();
        }

    }

    /**
     * 季度枚举
     */
    @Getter
    @RequiredArgsConstructor
    public enum Quarter {
        Q1(1, "第一季度", 1, 3),
        Q2(2, "第二季度", 4, 6),
        Q3(3, "第三季度", 7, 9),
        Q4(4, "第四季度", 10, 12);

        private final int value;
        private final String name;
        private final int startMonth;
        private final int endMonth;

        public static Quarter of(int value) {
            for (Quarter quarter : values()) {
                if (quarter.value == value) {
                    return quarter;
                }
            }
            throw new IllegalArgumentException("Invalid quarter value: " + value);
        }

        public static Quarter ofMonth(int month) {
            if (month < 1 || month > 12) {
                throw new IllegalArgumentException("Invalid month: " + month);
            }
            return of((month - 1) / 3 + 1);
        }
    }

    // ==================== 常量定义 ====================
    /**
     * 系统默认时区
     */
    private static final ZoneId ZONE_ID = ZoneId.systemDefault();
    // ==================== 类型转换 ====================

    /**
     * LocalDate 转 Date
     *
     * @param localDate LocalDate对象
     * @return Date对象(当天开始时间 00:00:00)
     */
    public static Date toDate(LocalDate localDate) {
        return localDate == null ? null : Date.from(localDate.atStartOfDay(ZONE_ID).toInstant());
    }

    /**
     * LocalDate 转 Date
     *
     * @param localDate LocalDate对象
     * @param zoneId    zoneId对象
     * @return Date对象(当天开始时间 00:00:00)
     */
    public static Date toDate(LocalDate localDate, ZoneId zoneId) {
        return localDate == null ? null : Date.from(localDate.atStartOfDay(zoneId).toInstant());
    }

    /**
     * LocalDateTime 转 Date
     *
     * @param localDateTime LocalDateTime对象
     * @param zoneId        zoneId对象
     * @return Date对象
     */
    public static Date toDate(LocalDateTime localDateTime, ZoneId zoneId) {
        return localDateTime == null ? null : Date.from(localDateTime.atZone(zoneId).toInstant());
    }

    /**
     * LocalDateTime 转 Date
     *
     * @param localDateTime LocalDateTime对象
     * @return Date对象
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return localDateTime == null ? null : Date.from(localDateTime.atZone(ZONE_ID).toInstant());
    }

    /**
     * Date 转 LocalDate
     *
     * @param date Date对象
     * @return LocalDate对象
     */
    public static LocalDate toLocalDate(Date date) {
        return date == null ? null : Instant.ofEpochMilli(date.getTime()).atZone(ZONE_ID).toLocalDate();
    }

    /**
     * Date 转 LocalDate
     *
     * @param date   Date对象
     * @param zoneId zoneId对象
     * @return LocalDate对象
     */
    public static LocalDate toLocalDate(Date date, ZoneId zoneId) {
        return date == null ? null : Instant.ofEpochMilli(date.getTime()).atZone(zoneId).toLocalDate();
    }

    /**
     * Date 转 LocalDateTime
     *
     * @param date Date对象
     * @return LocalDateTime对象
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return date == null ? null : Instant.ofEpochMilli(date.getTime()).atZone(ZONE_ID).toLocalDateTime();
    }

    /**
     * Date 转 LocalDateTime
     *
     * @param date   Date对象
     * @param zoneId zoneId对象
     * @return LocalDateTime对象
     */
    public static LocalDateTime toLocalDateTime(Date date, ZoneId zoneId) {
        return date == null ? null : Instant.ofEpochMilli(date.getTime()).atZone(zoneId).toLocalDateTime();
    }

    /**
     * LocalDateTime 转 LocalDate
     *
     * @param localDateTime LocalDateTime对象
     * @return LocalDate对象
     */
    public static LocalDate toLocalDate(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.toLocalDate();
    }

    /**
     * 时间戳(毫秒)转 Date
     *
     * @param timestamp 时间戳(毫秒)
     * @return Date对象
     */
    public static Date toDate(long timestamp) {
        return new Date(timestamp);
    }

    /**
     * 时间戳(毫秒)转 LocalDateTime
     *
     * @param timestamp 时间戳(毫秒)
     * @return LocalDateTime对象
     */
    public static LocalDateTime toLocalDateTime(long timestamp) {
        return Instant.ofEpochMilli(timestamp)
                .atZone(ZONE_ID)
                .toLocalDateTime();
    }

    /**
     * 时间戳(毫秒)转 LocalDateTime
     *
     * @param timestamp 时间戳(毫秒)
     * @param zoneId    zoneId对象
     * @return LocalDateTime对象
     */
    public static LocalDateTime toLocalDateTime(long timestamp, ZoneId zoneId) {
        return Instant.ofEpochMilli(timestamp)
                .atZone(zoneId)
                .toLocalDateTime();
    }

    // ==================== 获取当前时间 ====================

    /**
     * 获取当前日期
     *
     * @return LocalDate对象
     */
    public static LocalDate now() {
        return LocalDate.now();
    }

    /**
     * 获取当前日期时间
     *
     * @return LocalDateTime对象
     */
    public static LocalDateTime nowDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 获取当前日期(Date)
     *
     * @return Date对象
     */
    public static Date currentDate() {
        return new Date();
    }

    /**
     * 获取当前时间戳(毫秒)
     *
     * @return 时间戳
     */
    public static long currentTimestamp() {
        return System.currentTimeMillis();
    }

    // ==================== 时间范围获取 ====================

    /**
     * 获取时间范围的开始和结束时间
     *
     * @param timeRange 时间范围枚举
     * @return 数组 [开始时间, 结束时间]
     */
    public static Date[] getTimeRange(TimeRange timeRange) {
        if (timeRange == null) {
            return new Date[]{null, null};
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start;
        LocalDateTime end;

        switch (timeRange) {
            case TODAY:
                start = now.with(LocalTime.MIN);
                end = now.with(LocalTime.MAX);
                break;

            case YESTERDAY:
                start = now.minusDays(1).with(LocalTime.MIN);
                end = now.minusDays(1).with(LocalTime.MAX);
                break;

            case THIS_WEEK:
                start = now.minusDays(now.getDayOfWeek().getValue() - 1).with(LocalTime.MIN);
                end = now.plusDays(7 - now.getDayOfWeek().getValue()).with(LocalTime.MAX);
                break;

            case LAST_WEEK:
                LocalDateTime lastWeek = now.minusWeeks(1);
                start = lastWeek.minusDays(lastWeek.getDayOfWeek().getValue() - 1).with(LocalTime.MIN);
                end = lastWeek.plusDays(7 - lastWeek.getDayOfWeek().getValue()).with(LocalTime.MAX);
                break;

            case THIS_MONTH:
                start = now.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
                end = now.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
                break;

            case LAST_MONTH:
                LocalDateTime lastMonth = now.minusMonths(1);
                start = lastMonth.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
                end = lastMonth.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
                break;

            case THIS_QUARTER:
                Quarter currentQuarter = Quarter.ofMonth(now.getMonthValue());
                start = LocalDate.of(now.getYear(), currentQuarter.getStartMonth(), 1).atStartOfDay();
                end = LocalDate.of(now.getYear(), currentQuarter.getEndMonth(), 1)
                        .with(TemporalAdjusters.lastDayOfMonth())
                        .atTime(LocalTime.MAX);
                break;

            case LAST_QUARTER:
                int lastQuarterValue = Quarter.ofMonth(now.getMonthValue()).getValue() - 1;
                if (lastQuarterValue == 0) {
                    lastQuarterValue = 4;
                    now = now.minusYears(1);
                }
                Quarter lastQ = Quarter.of(lastQuarterValue);
                start = LocalDate.of(now.getYear(), lastQ.getStartMonth(), 1).atStartOfDay();
                end = LocalDate.of(now.getYear(), lastQ.getEndMonth(), 1)
                        .with(TemporalAdjusters.lastDayOfMonth())
                        .atTime(LocalTime.MAX);
                break;

            case THIS_YEAR:
                start = now.with(TemporalAdjusters.firstDayOfYear()).with(LocalTime.MIN);
                end = now.with(TemporalAdjusters.lastDayOfYear()).with(LocalTime.MAX);
                break;

            case LAST_YEAR:
                LocalDateTime lastYear = now.minusYears(1);
                start = lastYear.with(TemporalAdjusters.firstDayOfYear()).with(LocalTime.MIN);
                end = lastYear.with(TemporalAdjusters.lastDayOfYear()).with(LocalTime.MAX);
                break;

            case RECENT_7_DAYS:
                start = now.minusDays(6).with(LocalTime.MIN);
                end = now.with(LocalTime.MAX);
                break;

            case RECENT_30_DAYS:
                start = now.minusDays(29).with(LocalTime.MIN);
                end = now.with(LocalTime.MAX);
                break;

            case RECENT_90_DAYS:
                start = now.minusDays(89).with(LocalTime.MIN);
                end = now.with(LocalTime.MAX);
                break;

            case RECENT_180_DAYS:
                start = now.minusDays(179).with(LocalTime.MIN);
                end = now.with(LocalTime.MAX);
                break;

            case RECENT_365_DAYS:
                start = now.minusDays(364).with(LocalTime.MIN);
                end = now.with(LocalTime.MAX);
                break;

            default:
                return new Date[]{null, null};
        }

        return new Date[]{toDate(start), toDate(end)};
    }

    /**
     * 获取今年开始时间
     *
     * @return Date对象(yyyy-01-01 00:00:00)
     */
    public static Date getYearStart() {
        return getTimeRange(TimeRange.THIS_YEAR)[0];
    }

    /**
     * 获取今年结束时间
     *
     * @return Date对象(yyyy-12-31 23:59:59.999)
     */
    public static Date getYearEnd() {
        return getTimeRange(TimeRange.THIS_YEAR)[1];
    }

    /**
     * 获取本月开始时间
     *
     * @return Date对象(yyyy-MM-01 00:00:00)
     */
    public static Date getMonthStart() {
        return getTimeRange(TimeRange.THIS_MONTH)[0];
    }

    /**
     * 获取本月结束时间
     *
     * @return Date对象(yyyy-MM-dd 23:59:59.999, dd为该月最后一天)
     */
    public static Date getMonthEnd() {
        return getTimeRange(TimeRange.THIS_MONTH)[1];
    }

    /**
     * 获取本周开始时间(周一)
     *
     * @return Date对象(周一 00:00:00)
     */
    public static Date getWeekStart() {
        return getTimeRange(TimeRange.THIS_WEEK)[0];
    }

    /**
     * 获取本周结束时间(周日)
     *
     * @return Date对象(周日 23:59:59.999)
     */
    public static Date getWeekEnd() {
        return getTimeRange(TimeRange.THIS_WEEK)[1];
    }

    /**
     * 获取今天开始时间
     *
     * @return Date对象(yyyy-MM-dd 00:00:00)
     */
    public static Date getDayStart() {
        return getTimeRange(TimeRange.TODAY)[0];
    }

    /**
     * 获取今天结束时间
     *
     * @return Date对象(yyyy-MM-dd 23:59:59.999)
     */
    public static Date getDayEnd() {
        return getTimeRange(TimeRange.TODAY)[1];
    }

    /**
     * 获取指定日期的开始时间
     *
     * @param date 日期
     * @return Date对象(yyyy-MM-dd 00:00:00)
     */
    public static Date getDayStart(Date date) {
        if (date == null) return null;
        return toDate(toLocalDateTime(date).with(LocalTime.MIN));
    }

    /**
     * 获取指定日期的结束时间
     *
     * @param date 日期
     * @return Date对象(yyyy-MM-dd 23:59:59.999)
     */
    public static Date getDayEnd(Date date) {
        if (date == null) return null;
        return toDate(toLocalDateTime(date).with(LocalTime.MAX));
    }
    // ==================== 日期格式化(使用枚举) ====================

    /**
     * 格式化日期
     *
     * @param date    Date对象
     * @param pattern 格式枚举
     * @return 格式化字符串
     */
    public static String format(Date date, Pattern pattern) {
        if (date == null || pattern == null) return null;
        return toLocalDateTime(date).format(pattern.getFormatter());
    }

    /**
     * 格式化日期
     *
     * @param localDateTime LocalDateTime对象
     * @param pattern       格式枚举
     * @return 格式化字符串
     */
    public static String format(LocalDateTime localDateTime, Pattern pattern) {
        if (localDateTime == null || pattern == null) return null;
        return localDateTime.format(pattern.getFormatter());
    }

    /**
     * 格式化日期
     *
     * @param localDate LocalDate对象
     * @param pattern   格式枚举
     * @return 格式化字符串
     */
    public static String format(LocalDate localDate, Pattern pattern) {
        if (localDate == null || pattern == null) return null;
        return localDate.format(pattern.getFormatter());
    }

    /**
     * 格式化日期为 yyyy-MM-dd
     *
     * @param date Date对象
     * @return 格式化字符串
     */
    public static String formatDate(Date date) {
        return format(date, Pattern.DATE);
    }

    /**
     * 格式化日期为 yyyy-MM-dd
     *
     * @param localDate LocalDate对象
     * @return 格式化字符串
     */
    public static String formatDate(LocalDate localDate) {
        return format(localDate, Pattern.DATE);
    }

    /**
     * 格式化日期时间为 yyyy-MM-dd HH:mm:ss
     *
     * @param date Date对象
     * @return 格式化字符串
     */
    public static String formatDateTime(Date date) {
        return format(date, Pattern.DATETIME);
    }

    /**
     * 格式化日期时间为 yyyy-MM-dd HH:mm:ss
     *
     * @param localDateTime LocalDateTime对象
     * @return 格式化字符串
     */
    public static String formatDateTime(LocalDateTime localDateTime) {
        return format(localDateTime, Pattern.DATETIME);
    }

    /**
     * 按自定义格式格式化日期
     *
     * @param date    Date对象
     * @param pattern 格式字符串
     * @return 格式化字符串
     */
    public static String format(Date date, String pattern) {
        if (date == null || pattern == null) return null;
        return toLocalDateTime(date).format(DateTimeFormatter.ofPattern(pattern));
    }
    // ==================== 日期解析(使用枚举) ====================

    /**
     * 解析日期字符串
     *
     * @param dateStr 日期字符串
     * @param pattern 格式枚举
     * @return Date对象,解析失败返回null
     */
    public static Date parse(String dateStr, Pattern pattern) {
        if (dateStr == null || pattern == null || dateStr.trim().isEmpty()) {
            return null;
        }
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(dateStr.trim(), pattern.getFormatter());
            return toDate(localDateTime);
        } catch (Exception e) {
            // 尝试解析为日期(无时间部分)
            try {
                LocalDate localDate = LocalDate.parse(dateStr.trim(), pattern.getFormatter());
                return toDate(localDate);
            } catch (Exception ex) {
                return null;
            }
        }
    }

    /**
     * 解析日期字符串(yyyy-MM-dd HH:mm:ss)
     *
     * @param dateTimeStr 日期时间字符串
     * @return Date对象,解析失败返回null
     */
    public static Date parseDateTime(String dateTimeStr) {
        return parse(dateTimeStr, Pattern.DATETIME);
    }

    /**
     * 解析日期字符串(yyyy-MM-dd)
     *
     * @param dateStr 日期字符串
     * @return Date对象,解析失败返回null
     */
    public static Date parseDate(String dateStr) {
        return parse(dateStr, Pattern.DATE);
    }

    /**
     * 智能解析日期字符串(自动识别常见格式)
     *
     * @param dateStr 日期字符串
     * @return Date对象,解析失败返回null
     */
    public static Date parseAuto(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }

        // 依次尝试各种格式
        for (Pattern pattern : Pattern.values()) {
            Date result = parse(dateStr, pattern);
            if (result != null) {
                return result;
            }
        }

        return null;
    }

    // ==================== 日期计算(使用枚举) ====================

    /**
     * 增加时间
     *
     * @param date   原日期
     * @param amount 数量(可为负数)
     * @param unit   时间单位枚举
     * @return 新日期
     */
    public static Date plus(Date date, long amount, TimeUnit unit) {
        if (date == null || unit == null) return null;
        return toDate(toLocalDateTime(date).plus(amount, unit.getChronoUnit()));
    }

    /**
     * 减少时间
     *
     * @param date   原日期
     * @param amount 数量
     * @param unit   时间单位枚举
     * @return 新日期
     */
    public static Date minus(Date date, long amount, TimeUnit unit) {
        return plus(date, -amount, unit);
    }

    /**
     * 增加天数
     *
     * @param date 原日期
     * @param days 天数(可为负数)
     * @return 新日期
     */
    public static Date plusDays(Date date, int days) {
        return plus(date, days, TimeUnit.DAYS);
    }

    /**
     * 增加小时
     *
     * @param date  原日期
     * @param hours 小时数(可为负数)
     * @return 新日期
     */
    public static Date plusHours(Date date, int hours) {
        return plus(date, hours, TimeUnit.HOURS);
    }

    /**
     * 增加分钟
     *
     * @param date    原日期
     * @param minutes 分钟数(可为负数)
     * @return 新日期
     */
    public static Date plusMinutes(Date date, int minutes) {
        return plus(date, minutes, TimeUnit.MINUTES);
    }

    /**
     * 增加月份
     *
     * @param date   原日期
     * @param months 月数(可为负数)
     * @return 新日期
     */
    public static Date plusMonths(Date date, int months) {
        return plus(date, months, TimeUnit.MONTHS);
    }

    /**
     * 增加年份
     *
     * @param date  原日期
     * @param years 年数(可为负数)
     * @return 新日期
     */
    public static Date plusYears(Date date, int years) {
        return plus(date, years, TimeUnit.YEARS);
    }

    /**
     * 减少天数
     *
     * @param date 原日期
     * @param days 天数
     * @return 新日期
     */
    public static Date minusDays(Date date, int days) {
        return plusDays(date, -days);
    }

    /**
     * 减少小时
     *
     * @param date  原日期
     * @param hours 小时数
     * @return 新日期
     */
    public static Date minusHours(Date date, int hours) {
        return plusHours(date, -hours);
    }

    /**
     * 减少分钟
     *
     * @param date    原日期
     * @param minutes 分钟数
     * @return 新日期
     */
    public static Date minusMinutes(Date date, int minutes) {
        return plusMinutes(date, -minutes);
    }
    // ==================== 日期比较(使用枚举) ====================

    /**
     * 计算两个日期的时间差
     *
     * @param start 开始日期
     * @param end   结束日期
     * @param unit  时间单位枚举
     * @return 时间差(end - start)
     */
    public static long between(Date start, Date end, TimeUnit unit) {
        if (start == null || end == null || unit == null) return 0;
        return unit.getChronoUnit().between(toLocalDateTime(start), toLocalDateTime(end));
    }

    /**
     * 判断日期是否在指定范围内(包含边界)
     *
     * @param date  待判断日期
     * @param start 开始日期
     * @param end   结束日期
     * @return true=在范围内
     */
    public static boolean isBetween(Date date, Date start, Date end) {
        if (date == null || start == null || end == null) return false;
        return !date.before(start) && !date.after(end);
    }

    /**
     * 判断日期是否是今天
     *
     * @param date 日期
     * @return true=今天
     */
    public static boolean isToday(Date date) {
        return date != null && toLocalDate(date).equals(LocalDate.now());
    }

    /**
     * 判断两个日期是否是同一天
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return true=同一天
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) return false;
        return toLocalDate(date1).equals(toLocalDate(date2));
    }

    /**
     * 计算两个日期相差天数
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return 相差天数(end - start)
     */
    public static long daysBetween(Date start, Date end) {
        return between(start, end, TimeUnit.DAYS);
    }

    /**
     * 计算两个日期相差小时数
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return 相差小时数(end - start)
     */
    public static long hoursBetween(Date start, Date end) {
        return between(start, end, TimeUnit.HOURS);
    }

    /**
     * 计算两个日期相差分钟数
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return 相差分钟数(end - start)
     */
    public static long minutesBetween(Date start, Date end) {
        return between(start, end, TimeUnit.MINUTES);
    }
    // ==================== 获取日期字段 ====================

    /**
     * 获取年份
     *
     * @param date 日期
     * @return 年份
     */
    public static int getYear(Date date) {
        return date == null ? 0 : toLocalDateTime(date).getYear();
    }

    /**
     * 获取月份(1-12)
     *
     * @param date 日期
     * @return 月份
     */
    public static int getMonth(Date date) {
        return date == null ? 0 : toLocalDateTime(date).getMonthValue();
    }

    /**
     * 获取日(1-31)
     *
     * @param date 日期
     * @return 日
     */
    public static int getDay(Date date) {
        return date == null ? 0 : toLocalDateTime(date).getDayOfMonth();
    }

    /**
     * 获取小时(0-23)
     *
     * @param date 日期
     * @return 小时
     */
    public static int getHour(Date date) {
        return date == null ? 0 : toLocalDateTime(date).getHour();
    }

    /**
     * 获取分钟(0-59)
     *
     * @param date 日期
     * @return 分钟
     */
    public static int getMinute(Date date) {
        return date == null ? 0 : toLocalDateTime(date).getMinute();
    }

    /**
     * 获取秒(0-59)
     *
     * @param date 日期
     * @return 秒
     */
    public static int getSecond(Date date) {
        return date == null ? 0 : toLocalDateTime(date).getSecond();
    }

    /**
     * 获取星期几(1-7, 1=周一, 7=周日)
     *
     * @param date 日期
     * @return 星期
     */
    public static int getDayOfWeek(Date date) {
        return date == null ? 0 : toLocalDate(date).getDayOfWeek().getValue();
    }

    /**
     * 获取星期几枚举
     *
     * @param date 日期
     */
    public static Weekday getWeekday(Date date) {
        return date == null ? null : Weekday.getWeekday(getDayOfWeek(date));
    }

    /**
     * 获取该年的第几天(1-365/366)
     *
     * @param date 日期
     * @return 天数
     */
    public static int getDayOfYear(Date date) {
        return date == null ? 0 : toLocalDate(date).getDayOfYear();
    }
    // ==================== 便捷方法 ====================

    /**
     * 获取最近N天的日期(yyyy-MM-dd)
     *
     * @param days 天数
     * @return 日期字符串
     */
    public static String getRecentDays(int days) {
        return LocalDate.now().minusDays(days).format(Pattern.DATE.getFormatter());
    }

    /**
     * 判断是否是周末
     *
     * @param date 日期
     * @return true=周末(周六或周日)
     */
    public static boolean isWeekend(Date date) {
        if (date == null) return false;
        int day = getDayOfWeek(date);
        return day == 6 || day == 7;
    }

    /**
     * 判断是否是工作日
     *
     * @param date 日期
     */
    public static boolean isWorkday(Date date) {
        return !isWeekend(date);
    }

    /**
     * 判断是否是闰年
     *
     * @param date 日期
     * @return true=闰年
     */
    public static boolean isLeapYear(Date date) {
        return date != null && toLocalDate(date).isLeapYear();
    }

    /**
     * 获取该月的天数
     *
     * @param date 日期
     * @return 天数(28-31)
     */
    public static int getDaysOfMonth(Date date) {
        return date == null ? 0 : toLocalDate(date).lengthOfMonth();
    }

    /**
     * 获取年龄
     *
     * @param birthDate 出生日期
     * @return 年龄
     */
    public static int getAge(Date birthDate) {
        if (birthDate == null) return 0;
        return (int) ChronoUnit.YEARS.between(toLocalDate(birthDate), LocalDate.now());
    }

    // ==================== 扩展功能 ====================

    /**
     * 获取季度(1-4)
     *
     * @param date 日期
     * @return 所属季度 (1=Q1, 2=Q2, 3=Q3, 4=Q4)
     */
    public static Quarter getQuarter(Date date) {
        return date == null ? null : Quarter.ofMonth(getMonth(date));
    }


    /**
     * 获取季度开始时间
     *
     * @param date 日期
     * @return 季度第一天 00:00:00
     */
    public static Date getQuarterStart(Date date) {
        if (date == null) return null;
        Quarter quarter = getQuarter(date);
        return toDate(LocalDate.of(getYear(date), quarter.getStartMonth(), 1).atStartOfDay());
    }

    /**
     * 获取季度结束时间
     *
     * @param date 日期
     * @return 季度最后一天 23:59:59.999
     */
    public static Date getQuarterEnd(Date date) {
        if (date == null) return null;
        Quarter quarter = getQuarter(date);
        LocalDate lastDay = LocalDate.of(getYear(date), quarter.getEndMonth(), 1)
                .with(TemporalAdjusters.lastDayOfMonth());
        return toDate(lastDay.atTime(LocalTime.MAX));
    }

    /**
     * 获取友好时间描述
     * 例如: 刚刚 / 5分钟前 / 昨天 / 3天前 / 2个月前 / 去年
     *
     * @param date 日期
     * @return 友好描述字符串
     */
    public static String formatFriendly(Date date) {
        if (date == null) {
            return "";
        }
        long diffMillis = System.currentTimeMillis() - date.getTime();
        if (diffMillis < 0) return "未来";

        long seconds = diffMillis / 1000;
        if (seconds < 60) return "刚刚";
        if (seconds < 3600) return (seconds / 60) + "分钟前";
        if (seconds < 86400) return (seconds / 3600) + "小时前";

        long days = seconds / 86400;
        if (days == 1) return "昨天";
        if (days < 30) return days + "天前";
        if (days < 365) return (days / 30) + "个月前";
        return (days / 365) + "年前";
    }

    /**
     * 获取秒级时间戳
     *
     * @param date 日期
     * @return 秒级时间戳(long)
     */
    public static long toTimestampSeconds(Date date) {
        return date == null ? 0 : date.toInstant().getEpochSecond();
    }

    /**
     * 转为 ISO8601 标准时间字符串 (含时区)
     *
     * @param date 日期
     * @return ISO8601格式字符串，如 2025-11-12T18:00:00+08:00
     */
    public static String toISO8601String(Date date) {
        return date == null ? null :
                toLocalDateTime(date).atZone(ZONE_ID).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }


    /**
     * 严格解析日期字符串
     * 若格式不匹配则抛出 IllegalArgumentException
     *
     * @param dateStr 日期字符串
     * @param pattern 格式枚举
     * @return Date对象
     * @throws IllegalArgumentException 格式错误时抛出
     */
    public static Date parseStrict(String dateStr, Pattern pattern) {
        if (dateStr == null || pattern == null || dateStr.trim().isEmpty()) {
            throw new IllegalArgumentException("日期字符串或格式不能为空");
        }
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(dateStr.trim(), pattern.getFormatter());
            return toDate(localDateTime);
        } catch (Exception e) {
            try {
                LocalDate localDate = LocalDate.parse(dateStr.trim(), pattern.getFormatter());
                return toDate(localDate);
            } catch (Exception ex) {
                throw new IllegalArgumentException("日期格式不匹配: " + pattern.getPattern());
            }
        }
    }

    // ==================== 实用方法 ====================

    /**
     * 判断是否是过去的日期
     */
    public static boolean isPast(Date date) {
        return date != null && date.before(new Date());
    }

    /**
     * 判断是否是将来的日期
     */
    public static boolean isFuture(Date date) {
        return date != null && date.after(new Date());
    }

    /**
     * 获取两个日期之间的所有日期
     */
    public static List<Date> getDatesBetween(Date start, Date end) {
        List<Date> dates = new ArrayList<>();
        if (start == null || end == null || start.after(end)) {
            return dates;
        }

        LocalDate startDate = toLocalDate(start);
        LocalDate endDate = toLocalDate(end);

        while (!startDate.isAfter(endDate)) {
            dates.add(toDate(startDate));
            startDate = startDate.plusDays(1);
        }

        return dates;
    }

    /**
     * 获取两个日期之间的工作日数量
     */
    public static int getWorkdaysBetween(Date start, Date end) {
        if (start == null || end == null) return 0;

        List<Date> dates = getDatesBetween(start, end);
        return (int) dates.stream().filter(DateUtil::isWorkday).count();
    }

    /**
     * 获取下一个工作日
     */
    public static Date getNextWorkday(Date date) {
        if (date == null) return null;

        Date next = plusDays(date, 1);
        while (isWeekend(next)) {
            next = plusDays(next, 1);
        }
        return next;
    }

    /**
     * 获取上一个工作日
     */
    public static Date getPreviousWorkday(Date date) {
        if (date == null) return null;

        Date prev = minusDays(date, 1);
        while (isWeekend(prev)) {
            prev = minusDays(prev, 1);
        }
        return prev;
    }

    /**
     * 计算N个工作日后的日期
     */
    public static Date plusWorkdays(Date date, int workdays) {
        if (date == null) return null;

        Date result = date;
        int count = 0;

        while (count < workdays) {
            result = plusDays(result, 1);
            if (isWorkday(result)) {
                count++;
            }
        }

        return result;
    }

    /**
     * 判断是否是同一周
     */
    public static boolean isSameWeek(Date date1, Date date2) {
        if (date1 == null || date2 == null) return false;

        LocalDate ld1 = toLocalDate(date1);
        LocalDate ld2 = toLocalDate(date2);

        return ld1.get(java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR) ==
                ld2.get(java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR) &&
                ld1.get(java.time.temporal.IsoFields.WEEK_BASED_YEAR) ==
                        ld2.get(java.time.temporal.IsoFields.WEEK_BASED_YEAR);
    }

    /**
     * 判断是否是同一月
     */
    public static boolean isSameMonth(Date date1, Date date2) {
        if (date1 == null || date2 == null) return false;

        LocalDate ld1 = toLocalDate(date1);
        LocalDate ld2 = toLocalDate(date2);

        return ld1.getYear() == ld2.getYear() && ld1.getMonthValue() == ld2.getMonthValue();
    }

    /**
     * 判断是否是同一年
     */
    public static boolean isSameYear(Date date1, Date date2) {
        if (date1 == null || date2 == null) return false;
        return getYear(date1) == getYear(date2);
    }

    /**
     * 获取指定月份的第一天
     */
    public static Date getFirstDayOfMonth(int year, int month) {
        return toDate(LocalDate.of(year, month, 1).atStartOfDay());
    }

    /**
     * 获取指定月份的最后一天
     */
    public static Date getLastDayOfMonth(int year, int month) {
        LocalDate lastDay = LocalDate.of(year, month, 1).with(TemporalAdjusters.lastDayOfMonth());
        return toDate(lastDay.atTime(LocalTime.MAX));
    }

    /**
     * 获取指定日期所在月份的所有日期
     */
    public static List<Date> getDatesOfMonth(Date date) {
        if (date == null) return new ArrayList<>();

        Date start = toDate(toLocalDate(date).with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay());
        Date end = toDate(toLocalDate(date).with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX));

        return getDatesBetween(start, end);
    }

    /**
     * 获取年龄（精确到月）
     */
    public static String getAgeDetail(Date birthDate) {
        if (birthDate == null) return "";

        LocalDate birth = toLocalDate(birthDate);
        LocalDate now = LocalDate.now();

        long years = ChronoUnit.YEARS.between(birth, now);
        long months = ChronoUnit.MONTHS.between(birth.plusYears(years), now);

        if (years == 0) {
            return months + "个月";
        }

        return months == 0 ? years + "岁" : years + "岁" + months + "个月";
    }

    /**
     * 判断日期是否在今天之后（不包括今天）
     */
    public static boolean isAfterToday(Date date) {
        if (date == null) return false;
        return toLocalDate(date).isAfter(LocalDate.now());
    }

    /**
     * 获取本周的所有日期
     */
    public static List<Date> getDatesOfWeek(Date date) {
        if (date == null) return new ArrayList<>();
        Date[] range = getTimeRange(TimeRange.THIS_WEEK);
        return getDatesBetween(range[0], range[1]);
    }

    /**
     * 获取指定日期所在周的星期N的日期
     *
     * @param date    参考日期
     * @param weekday 星期枚举
     * @return 该周指定星期的日期
     */
    public static Date getDateOfWeek(Date date, Weekday weekday) {
        if (date == null || weekday == null) return null;
        LocalDate localDate = toLocalDate(date);
        int currentDay = localDate.getDayOfWeek().getValue();
        int targetDay = weekday.getValue();
        int diff = targetDay - currentDay;
        return toDate(localDate.plusDays(diff).atStartOfDay());
    }

    /**
     * 获取年龄段描述
     *
     * @param birthDate 出生日期
     * @return 年龄段描述：婴儿/幼儿/儿童/少年/青年/中年/老年
     */
    public static String getAgeGroup(Date birthDate) {
        if (birthDate == null) return "";
        int age = getAge(birthDate);
        if (age < 1) return "婴儿";
        if (age < 3) return "幼儿";
        if (age < 12) return "儿童";
        if (age < 18) return "少年";
        if (age < 35) return "青年";
        if (age < 60) return "中年";
        return "老年";
    }

    /**
     * 获取生肖
     *
     * @param date 日期
     * @return 生肖（鼠/牛/虎...）
     */
    public static String getZodiac(Date date) {
        if (date == null) return "";
        String[] zodiacs = {"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};
        int year = getYear(date);
        return zodiacs[year % 12];
    }

    /**
     * 获取星座
     *
     * @param date 日期
     * @return 星座名称
     */
    public static String getConstellation(Date date) {
        if (date == null) return "";

        int month = getMonth(date);
        int day = getDay(date);

        String[] constellations = {
                "摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座",
                "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"
        };

        int[] days = {20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22};

        return day < days[month - 1] ? constellations[month - 1] : constellations[month];
    }

    /**
     * 计算两个日期之间相差的时间（带单位）
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 时间差描述，如：2天3小时15分钟
     */
    public static String formatDuration(Date start, Date end) {
        if (start == null || end == null) return "";

        long diffMillis = end.getTime() - start.getTime();
        if (diffMillis < 0) {
            diffMillis = -diffMillis;
        }

        long days = diffMillis / (24 * 60 * 60 * 1000);
        diffMillis %= (24 * 60 * 60 * 1000);

        long hours = diffMillis / (60 * 60 * 1000);
        diffMillis %= (60 * 60 * 1000);

        long minutes = diffMillis / (60 * 1000);
        diffMillis %= (60 * 1000);

        long seconds = diffMillis / 1000;

        StringBuilder sb = new StringBuilder();
        if (days > 0) sb.append(days).append("天");
        if (hours > 0) sb.append(hours).append("小时");
        if (minutes > 0) sb.append(minutes).append("分钟");
        if (seconds > 0 && days == 0) sb.append(seconds).append("秒");

        return sb.length() == 0 ? "0秒" : sb.toString();
    }

    /**
     * 格式化时长（毫秒转可读格式）
     *
     * @param millis 毫秒数
     * @return 格式化字符串，如：1小时30分钟
     */
    public static String formatDuration(long millis) {
        if (millis < 0) millis = 0;

        long days = millis / (24 * 60 * 60 * 1000);
        millis %= (24 * 60 * 60 * 1000);

        long hours = millis / (60 * 60 * 1000);
        millis %= (60 * 60 * 1000);

        long minutes = millis / (60 * 1000);
        millis %= (60 * 1000);

        long seconds = millis / 1000;

        StringBuilder sb = new StringBuilder();
        if (days > 0) sb.append(days).append("天");
        if (hours > 0) sb.append(hours).append("小时");
        if (minutes > 0) sb.append(minutes).append("分钟");
        if (seconds > 0 && days == 0 && hours == 0) sb.append(seconds).append("秒");

        return sb.length() == 0 ? "0秒" : sb.toString();
    }

    /**
     * 获取倒计时描述
     *
     * @param targetDate 目标日期
     * @return 倒计时描述，如：还剩2天3小时
     */
    public static String formatCountdown(Date targetDate) {
        if (targetDate == null) return "";

        long diffMillis = targetDate.getTime() - System.currentTimeMillis();

        if (diffMillis <= 0) {
            return "已结束";
        }

        long days = diffMillis / (24 * 60 * 60 * 1000);
        diffMillis %= (24 * 60 * 60 * 1000);

        long hours = diffMillis / (60 * 60 * 1000);
        diffMillis %= (60 * 60 * 1000);

        long minutes = diffMillis / (60 * 1000);

        StringBuilder sb = new StringBuilder("还剩");
        if (days > 0) {
            sb.append(days).append("天");
            if (hours > 0) sb.append(hours).append("小时");
        } else if (hours > 0) {
            sb.append(hours).append("小时");
            if (minutes > 0) sb.append(minutes).append("分钟");
        } else if (minutes > 0) {
            sb.append(minutes).append("分钟");
        } else {
            sb.append("不到1分钟");
        }

        return sb.toString();
    }

    /**
     * 获取当前日期是一年中的第几周
     */
    public static int getWeekOfYear(Date date) {
        if (date == null) return 0;
        return toLocalDate(date).get(java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR);
    }

    /**
     * 判断是否是月初（1-5号）
     */
    public static boolean isBeginningOfMonth(Date date) {
        if (date == null) return false;
        int day = getDay(date);
        return day >= 1 && day <= 5;
    }

    /**
     * 判断是否是月末（最后5天）
     */
    public static boolean isEndOfMonth(Date date) {
        if (date == null) return false;
        int day = getDay(date);
        int daysInMonth = getDaysOfMonth(date);
        return day >= (daysInMonth - 4);
    }

    /**
     * 获取指定日期N天后的同一时间
     *
     * @param date 基准日期
     * @param days 天数
     * @return N天后的同一时间
     */
    public static Date getSameTimeAfterDays(Date date, int days) {
        return plusDays(date, days);
    }

    /**
     * 获取N个月后的同一天
     * 如果目标月份没有该天数，返回该月最后一天
     *
     * @param date   基准日期
     * @param months 月数
     * @return N个月后的日期
     */
    public static Date getSameDayAfterMonths(Date date, int months) {
        if (date == null) return null;

        LocalDate localDate = toLocalDate(date);
        int targetDay = localDate.getDayOfMonth();

        LocalDate result = localDate.plusMonths(months);
        int maxDay = result.lengthOfMonth();

        if (targetDay > maxDay) {
            result = result.withDayOfMonth(maxDay);
        }

        return toDate(result.atStartOfDay());
    }

    /**
     * 获取指定时间的时间戳范围（当天）
     *
     * @param date 日期
     * @return [开始时间戳, 结束时间戳] 单位：毫秒
     */
    public static long[] getTimestampRange(Date date) {
        if (date == null) return new long[]{0, 0};

        Date start = getDayStart(date);
        Date end = getDayEnd(date);

        return new long[]{start.getTime(), end.getTime()};
    }

    /**
     * 格式化为相对时间（中文）
     *
     * @param date 日期
     * @return 相对时间描述，如：今天 15:30、昨天 10:00、3天前
     */
    public static String formatRelative(Date date) {
        if (date == null) return "";

        LocalDate targetDate = toLocalDate(date);
        LocalDate today = LocalDate.now();

        long days = ChronoUnit.DAYS.between(targetDate, today);
        String time = format(date, Pattern.TIME_SHORT);

        if (days == 0) {
            return "今天 " + time;
        } else if (days == 1) {
            return "昨天 " + time;
        } else if (days == 2) {
            return "前天 " + time;
        } else if (days > 0 && days < 7) {
            return days + "天前";
        } else if (days < 0 && days > -7) {
            return Math.abs(days) + "天后";
        } else {
            return formatDate(date);
        }
    }

    /**
     * 设置时间为指定时分秒
     *
     * @param date   日期
     * @param hour   小时
     * @param minute 分钟
     * @param second 秒
     * @return 新日期
     */
    public static Date setTime(Date date, int hour, int minute, int second) {
        if (date == null) return null;

        LocalDateTime dateTime = toLocalDateTime(date);
        dateTime = dateTime.withHour(hour).withMinute(minute).withSecond(second).withNano(0);

        return toDate(dateTime);
    }

    /**
     * 判断是否在时间范围内（仅比较时分秒）
     *
     * @param time      待检查时间
     * @param startTime 开始时间（如：09:00:00）
     * @param endTime   结束时间（如：18:00:00）
     * @return true=在范围内
     */
    public static boolean isTimeInRange(Date time, String startTime, String endTime) {
        if (time == null || startTime == null || endTime == null) {
            return false;
        }

        LocalTime currentTime = toLocalDateTime(time).toLocalTime();
        LocalTime start = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalTime end = LocalTime.parse(endTime, DateTimeFormatter.ofPattern("HH:mm:ss"));

        return !currentTime.isBefore(start) && !currentTime.isAfter(end);
    }

    /**
     * 获取距离下次生日还有多少天
     */
    public static int getDaysUntilNextBirthday(Date birthDate) {
        if (birthDate == null) return -1;

        LocalDate birth = toLocalDate(birthDate);
        LocalDate today = LocalDate.now();

        LocalDate nextBirthday = birth.withYear(today.getYear());
        if (nextBirthday.isBefore(today) || nextBirthday.equals(today)) {
            nextBirthday = nextBirthday.plusYears(1);
        }

        return (int) ChronoUnit.DAYS.between(today, nextBirthday);
    }

    /**
     * 判断今天是否是生日
     */
    public static boolean isBirthdayToday(Date birthDate) {
        if (birthDate == null) return false;

        LocalDate birth = toLocalDate(birthDate);
        LocalDate today = LocalDate.now();

        return birth.getMonthValue() == today.getMonthValue()
                && birth.getDayOfMonth() == today.getDayOfMonth();
    }

    /**
     * 获取本周工作日列表
     */
    public static List<Date> getWorkdaysOfWeek() {
        Date[] weekRange = getTimeRange(TimeRange.THIS_WEEK);
        return getDatesBetween(weekRange[0], weekRange[1]).stream()
                .filter(DateUtil::isWorkday)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * 获取本月工作日列表
     */
    public static List<Date> getWorkdaysOfMonth() {
        Date[] monthRange = getTimeRange(TimeRange.THIS_MONTH);
        return getDatesBetween(monthRange[0], monthRange[1]).stream()
                .filter(DateUtil::isWorkday)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * 获取本月工作日天数
     */
    public static int getWorkdaysCountOfMonth() {
        return getWorkdaysOfMonth().size();
    }

    /**
     * 比较两个日期大小（忽略时间部分）
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return -1/0/1 分别表示 date1 < date2 / date1 == date2 / date1 > date2
     */
    public static int compareDateOnly(Date date1, Date date2) {
        if (date1 == null && date2 == null) return 0;
        if (date1 == null) return -1;
        if (date2 == null) return 1;

        LocalDate ld1 = toLocalDate(date1);
        LocalDate ld2 = toLocalDate(date2);

        return ld1.compareTo(ld2);
    }

    /**
     * 比较两个时间大小（忽略日期部分）
     *
     * @param time1 时间1
     * @param time2 时间2
     * @return -1/0/1 分别表示 time1 < time2 / time1 == time2 / time1 > time2
     */
    public static int compareTimeOnly(Date time1, Date time2) {
        if (time1 == null && time2 == null) return 0;
        if (time1 == null) return -1;
        if (time2 == null) return 1;

        LocalTime lt1 = toLocalDateTime(time1).toLocalTime();
        LocalTime lt2 = toLocalDateTime(time2).toLocalTime();

        return lt1.compareTo(lt2);
    }

    /**
     * 获取两个日期中较早的日期
     */
    public static Date min(Date date1, Date date2) {
        if (date1 == null) return date2;
        if (date2 == null) return date1;
        return date1.before(date2) ? date1 : date2;
    }

    /**
     * 获取两个日期中较晚的日期
     */
    public static Date max(Date date1, Date date2) {
        if (date1 == null) return date2;
        if (date2 == null) return date1;
        return date1.after(date2) ? date1 : date2;
    }

    /**
     * 批量格式化日期
     *
     * @param dates   日期列表
     * @param pattern 格式
     * @return 格式化后的字符串列表
     */
    public static List<String> batchFormat(List<Date> dates, Pattern pattern) {
        if (dates == null || dates.isEmpty()) {
            return new ArrayList<>();
        }

        return dates.stream()
                .map(date -> format(date, pattern))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * 批量解析日期字符串
     *
     * @param dateStrings 日期字符串列表
     * @param pattern     格式
     * @return 解析后的日期列表（解析失败的为null）
     */
    public static List<Date> batchParse(List<String> dateStrings, Pattern pattern) {
        if (dateStrings == null || dateStrings.isEmpty()) {
            return new ArrayList<>();
        }

        return dateStrings.stream()
                .map(str -> parse(str, pattern))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * 验证日期字符串格式是否正确
     *
     * @param dateStr 日期字符串
     * @param pattern 格式
     * @return true=格式正确
     */
    public static boolean isValidDate(String dateStr, Pattern pattern) {
        return parse(dateStr, pattern) != null;
    }

    /**
     * 获取当前时间的Unix时间戳（秒）
     */
    public static long currentUnixTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * Unix时间戳（秒）转Date
     */
    public static Date fromUnixTimestamp(long unixTimestamp) {
        return new Date(unixTimestamp * 1000);
    }

    /**
     * Date转Unix时间戳（秒）
     */
    public static long toUnixTimestamp(Date date) {
        return date == null ? 0 : date.getTime() / 1000;
    }

}
