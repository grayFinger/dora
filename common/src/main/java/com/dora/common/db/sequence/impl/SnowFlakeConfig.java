package com.dora.common.db.sequence.impl;

public class SnowFlakeConfig {
    private Integer datacenterId = 0;
    private Integer workerId = 0;

    public SnowFlakeConfig() {
    }

    public Integer getDatacenterId() {
        return this.datacenterId;
    }

    public void setDatacenterId(Integer datacenterId) {
        this.datacenterId = datacenterId;
    }

    public Integer getWorkerId() {
        return this.workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }
}
