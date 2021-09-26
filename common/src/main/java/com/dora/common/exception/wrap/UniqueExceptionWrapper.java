package com.dora.common.exception.wrap;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import com.dora.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class UniqueExceptionWrapper<T extends Exception> implements IExceptionWrapper<T> {
    private static Logger logger = LoggerFactory.getLogger(UniqueExceptionWrapper.class);
    private Map<String, String> errorMsgMap = new HashMap();

    public UniqueExceptionWrapper() {
    }

    public Throwable process(T e) {
        UniqueInfo uniqueInfo = this.parseKey(e.getMessage());
        String message = null;
        if (uniqueInfo != null) {
            logger.debug("解析数据库重复异常描述：{}，索引key：{}", e.getMessage(), uniqueInfo.getKey());
            if (uniqueInfo.getKey() != null) {
                message = (String)this.errorMsgMap.get(uniqueInfo.key);
                if (message != null) {
                    message = MessageFormat.format(message, uniqueInfo.getData(), uniqueInfo.getKey());
                }
            }
        }

        if (message == null) {
            message = "数据库存在重复记录";
        }

        return new BusinessException("7005", message);
    }

    public void register(String key, String message) {
        this.errorMsgMap.put(key, message);
    }

    protected abstract UniqueInfo parseKey(String message);

    public class UniqueInfo {
        private String key;
        private String data;

        public UniqueInfo(String key) {
            this.key = key;
        }

        public UniqueInfo(String key, String data) {
            this.key = key;
            this.data = data;
        }

        public String getKey() {
            return this.key;
        }

        public String getData() {
            return this.data;
        }
    }
}
