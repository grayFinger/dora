package com.dora.common.convert;

import com.github.pagehelper.util.StringUtil;
import java.text.ParseException;
import java.util.Date;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;

public class DateConverter implements Converter<String, Date> {
    private static Logger logger = LoggerFactory.getLogger(DateConverter.class);
    @Value("${spring.jackson.dateFormat:yyyy-MM-dd HH:mm:ss}")
    private String formatString;

    public DateConverter() {
    }

    public Date convert(String source) {
        if (StringUtil.isEmpty(source)) {
            return null;
        } else {
            String format = this.formatString.substring(0, source.length());

            try {
                return DateUtils.parseDate(source, new String[]{format});
            } catch (ParseException var4) {
                logger.error("转化日期[{}]=>[{}]失败", new Object[]{source, format, var4});
                return null;
            }
        }
    }
}
