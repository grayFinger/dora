package com.dora.common.config;

import java.util.ArrayList;
import java.util.List;

public class RequestConfig {
    private List<String> noFilterUrls = new ArrayList();
    private List<String> noFilterReferers = new ArrayList();

    public RequestConfig() {
    }

    public List<String> getNoFilterUrls() {
        return this.noFilterUrls;
    }

    public void setNoFilterUrls(List<String> noFilterUrls) {
        this.noFilterUrls = noFilterUrls;
    }

    public List<String> getNoFilterReferers() {
        return this.noFilterReferers;
    }

    public void setNoFilterReferers(List<String> noFilterReferers) {
        this.noFilterReferers = noFilterReferers;
    }
}
