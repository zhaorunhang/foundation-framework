package com.zrh.framework.core.validators;

import com.zrh.framework.core.annotations.DateRange;
import com.zrh.framework.core.utils.ReflectiveFieldHandlerUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @PACKAGE_NAME: com.zrh.framework.core.validators
 * @NAME: ZRH
 * @USER: ZRH13
 * @DATE: 2025/11/12
 * @TIME: 15:35
 * @Description:
 */
@Slf4j
public class DateRangeValidator implements ConstraintValidator<DateRange, Object> {

    private String startField;
    private String endField;
    private String pattern;

    @Override
    public void initialize(DateRange annotation) {
        this.startField = annotation.startField();
        this.endField = annotation.endField();
        this.pattern = annotation.pattern();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        try {
            final Object startValue = ReflectiveFieldHandlerUtil.getValueByPropName(value, startField, false, null);
            final Object endValue = ReflectiveFieldHandlerUtil.getValueByPropName(value, endField, false, null);

            // null 值由 @NotNull 处理
            if (startValue == null || endValue == null) {
                return true;
            }

            // 比较日期
            return compare(startValue, endValue, context);
        } catch (Exception e) {
            throw new ValidationException("日期比较失败: " + e.getMessage(), e);
        }
    }

    /**
     * 通用日期比较
     */
    private boolean compare(Object start, Object end, ConstraintValidatorContext context) {
        // 类型必须相同
        if (!start.getClass().equals(end.getClass())) {
            buildError(context, "开始和结束日期类型必须一致");
            return false;
        }

        try {
            if (start instanceof LocalDate) {
                return compareLocalDate((LocalDate) start, (LocalDate) end, context);
            } else if (start instanceof LocalDateTime) {
                return compareLocalDateTime((LocalDateTime) start, (LocalDateTime) end, context);
            } else if (start instanceof Date) {
                return compareDate((Date) start, (Date) end, context);
            } else if (start instanceof Instant) {
                return compareInstant((Instant) start, (Instant) end, context);
            } else if (start instanceof ZonedDateTime) {
                return compareZonedDateTime((ZonedDateTime) start, (ZonedDateTime) end, context);
            } else if (start instanceof String) {
                return compareString((String) start, (String) end, context);
            } else {
                buildError(context, "不支持的日期类型: " + start.getClass().getName());
                return false;
            }
        } catch (Exception e) {
            buildError(context, "日期比较失败: " + e.getMessage());
            return false;
        }
    }

    private boolean compareLocalDate(LocalDate start, LocalDate end, ConstraintValidatorContext context) {
        if (start.isAfter(end)) {
            buildError(context, String.format("开始日期 %s 不能晚于结束日期 %s", start, end));
            return false;
        }
        return true;
    }

    private boolean compareLocalDateTime(LocalDateTime start, LocalDateTime end, ConstraintValidatorContext context) {
        if (start.isAfter(end)) {
            buildError(context, String.format("开始时间 %s 不能晚于结束时间 %s", start, end));
            return false;
        }
        return true;
    }

    private boolean compareDate(Date start, Date end, ConstraintValidatorContext context) {
        if (start.after(end)) {
            buildError(context, String.format("开始时间 %s 不能晚于结束时间 %s", start, end));
            return false;
        }
        return true;
    }

    private boolean compareInstant(Instant start, Instant end, ConstraintValidatorContext context) {
        if (start.isAfter(end)) {
            buildError(context, String.format("开始时间 %s 不能晚于结束时间 %s", start, end));
            return false;
        }
        return true;
    }

    private boolean compareZonedDateTime(ZonedDateTime start, ZonedDateTime end, ConstraintValidatorContext context) {
        if (start.isAfter(end)) {
            buildError(context, String.format("开始时间 %s 不能晚于结束时间 %s", start, end));
            return false;
        }
        return true;
    }

    private boolean compareString(String start, String end, ConstraintValidatorContext context) {
        try {
            // 尝试多种格式解析
            LocalDateTime startDateTime = parseStringToDateTime(start);
            LocalDateTime endDateTime = parseStringToDateTime(end);

            if (startDateTime.isAfter(endDateTime)) {
                buildError(context, String.format("开始时间 %s 不能晚于结束时间 %s", start, end));
                return false;
            }
            return true;
        } catch (Exception e) {
            buildError(context, "日期字符串格式错误: " + e.getMessage());
            return false;
        }
    }

    /**
     * 解析字符串为 LocalDateTime
     * 支持多种常见格式
     */
    private LocalDateTime parseStringToDateTime(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            throw new IllegalArgumentException("日期字符串为空");
        }

        dateStr = dateStr.trim();

        // 使用自定义格式
        if (pattern != null && !pattern.isEmpty()) {
            return LocalDateTime.parse(dateStr, java.time.format.DateTimeFormatter.ofPattern(pattern));
        }

        // 尝试常见格式
        try {
            // ISO 格式: 2023-12-01T10:30:00
            if (dateStr.contains("T")) {
                return LocalDateTime.parse(dateStr);
            }

            // 日期格式: 2023-12-01
            if (dateStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return LocalDate.parse(dateStr).atStartOfDay();
            }

            // 日期时间格式: 2023-12-01 10:30:00
            if (dateStr.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
                return LocalDateTime.parse(dateStr,
                        java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }

            // 日期时间格式(无秒): 2023-12-01 10:30
            if (dateStr.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}")) {
                return LocalDateTime.parse(dateStr,
                        java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            }

            // 紧凑格式: 20231201
            if (dateStr.matches("\\d{8}")) {
                return LocalDate.parse(dateStr,
                        java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd")).atStartOfDay();
            }

            // 紧凑格式带时间: 20231201103000
            if (dateStr.matches("\\d{14}")) {
                return LocalDateTime.parse(dateStr,
                        java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            }

            throw new IllegalArgumentException("不支持的日期格式: " + dateStr);

        } catch (Exception e) {
            throw new IllegalArgumentException("日期解析失败: " + dateStr, e);
        }
    }

    /**
     * 构建自定义错误消息
     */
    private void buildError(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }

}
