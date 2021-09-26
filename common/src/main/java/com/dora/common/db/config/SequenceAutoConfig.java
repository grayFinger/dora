
package com.dora.common.db.config;


import com.dora.common.db.sequence.ISequence;
import com.dora.common.db.sequence.SequenceUtils;
import com.dora.common.db.sequence.impl.NullSequence;
import com.dora.common.db.sequence.impl.SnowFlakeConfig;
import com.dora.common.db.sequence.impl.SnowFlakeSequence;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SequenceAutoConfig {
    public SequenceAutoConfig() {
    }

    @Bean
    @ConditionalOnMissingBean({ISequence.class})
    @ConditionalOnProperty(
        name = {"sequence.type"},
        havingValue = "null"
    )
    public ISequence nullSequence() {
        ISequence sequence = (ISequence) new NullSequence();
        SequenceUtils.setSequence(sequence);
        return sequence;
    }

    @Bean
    @ConfigurationProperties(
        prefix = "sequence.snow-flake"
    )
    @ConditionalOnProperty(
        name = {"sequence.type"},
        havingValue = "snowFlake",
        matchIfMissing = true
    )
    public SnowFlakeConfig snowFlakeConfig() {
        return new SnowFlakeConfig();
    }

    @Bean
    @ConditionalOnMissingBean({ISequence.class})
    @ConditionalOnProperty(
        name = {"sequence.type"},
        havingValue = "snowFlake",
        matchIfMissing = true
    )
    public SnowFlakeSequence snowFlakeSequence(SnowFlakeConfig snowFlakeConfig) {
        SnowFlakeSequence sequence = new SnowFlakeSequence();
        Integer datacenterId = snowFlakeConfig.getDatacenterId();
        if (datacenterId >= 0 && datacenterId <= 31) {
            sequence.setDatacenterId((long)datacenterId);
            Integer workerId = snowFlakeConfig.getWorkerId();
            if (workerId >= 0 && workerId <= 31) {
                sequence.setWorkerId((long)workerId);
                SequenceUtils.setSequence((ISequence) sequence);
                return sequence;
            } else {
                throw new RuntimeException("工作站编号设置有误，数据范围为0~31");
            }
        } else {
            throw new RuntimeException("数据中心编号设置有误，数据范围为0~31");
        }
    }
}
