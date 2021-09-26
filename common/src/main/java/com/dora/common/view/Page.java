package com.dora.common.view;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class Page implements Serializable {
    @JsonIgnore
    @JSONField(
        serialize = false
    )
    @ApiModelProperty("页码")
    private Integer pageNo = 1;
    @JsonIgnore
    @JSONField(
        serialize = false
    )
    @ApiModelProperty("每页大小")
    private Integer pageSize = 10;
    @JsonIgnore
    @JSONField(
        serialize = false
    )
    @ApiModelProperty("开始记录数")
    private Integer start = 0;
    @JsonIgnore
    @JSONField(
        serialize = false
    )
    @ApiModelProperty("排序")
    private String orderBy;
    @JsonIgnore
    @JSONField(
        serialize = false
    )
    @ApiModelProperty(
        hidden = true
    )
    private Boolean count;

    public Page() {
    }

    public Page(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public Page(Integer pageNo, Integer pageSize, String orderBy) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
    }

    public Page(Integer pageNo, Integer pageSize, String orderBy, Boolean count) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
        this.count = count;
    }

    public Integer getPageNo() {
        return this.pageNo == 1 ? Double.valueOf(Math.ceil((double)(this.start + 1) * 1.0D / (double)this.pageSize)).intValue() : this.pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize > 0) {
            this.pageSize = pageSize;
        }

    }

    public String getOrderBy() {
        return this.orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Boolean getCount() {
        return this.count;
    }

    public void setCount(Boolean count) {
        this.count = count;
    }

    public Integer getStart() {
        return this.start == 0 ? this.pageSize * (this.pageNo - 1) : this.start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }
}
