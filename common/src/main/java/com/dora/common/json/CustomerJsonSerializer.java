package com.dora.common.json;

import com.dora.common.annotation.JSON;
import com.dora.common.annotation.JSONS;
import com.dora.common.config.JsonConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import org.apache.commons.lang.StringUtils;

public class CustomerJsonSerializer {
    ObjectMapper mapper = new ObjectMapper();
    JacksonJsonFilter jacksonFilter = new JacksonJsonFilter();

    public CustomerJsonSerializer(JsonConfig jsonConfig) {
        this.mapper.setDateFormat(new SimpleDateFormat(jsonConfig.getDateFormat()));
    }

    public void filter(Class<?> clazz, String include, String filter) {
        if (clazz != null) {
            if (StringUtils.isNotBlank(include)) {
                this.jacksonFilter.include(clazz, include.split("\\s*,\\s*"));
            }

            if (StringUtils.isNotBlank(filter)) {
                this.jacksonFilter.filter(clazz, filter.split("\\s*,\\s*"));
            }

            this.mapper.addMixIn(clazz, this.jacksonFilter.getClass());
        }
    }

    public String toJson(Object object) throws JsonProcessingException {
        this.mapper.setFilterProvider(this.jacksonFilter);
        return this.mapper.writeValueAsString(object);
    }

    public void filter(JSON json) {
        this.filter(json.value(), json.include(), json.filter());
    }

    public void filter(JSONS jsons) {
        if (jsons.value() != null && jsons.value().length > 0) {
            JSON[] var2 = jsons.value();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                JSON json = var2[var4];
                this.filter(json);
            }
        }

    }
}
