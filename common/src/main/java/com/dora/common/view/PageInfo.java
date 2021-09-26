//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dora.common.view;

import com.dora.common.utils.StringMap;
import com.github.pagehelper.Page;
import java.util.ArrayList;
import java.util.List;

public class PageInfo<T> extends StringMap {
    public PageInfo() {
        this.put("total", 0);
        this.put("rows", new ArrayList());
        this.put("pageNo", 1);
        this.put("firstPage", true);
        this.put("lastPage", true);
    }

    public PageInfo(List<T> rows, Boolean firstPage, Boolean lastPage, long total) {
        if (rows == null) {
            rows = new ArrayList();
        }

        this.put("total", total);
        this.put("rows", rows);
        this.put("firstPage", firstPage == null ? true : firstPage);
        this.put("lastPage", lastPage == null ? true : lastPage);
    }

    public PageInfo(List<T> rows, int start, int end, long total) {
        this();
        this.calc(rows, start, end, total);
    }

    public PageInfo(List<T> rows, long total) {
        this();
        if (rows != null) {
            int size = rows.size();
            this.calc(rows, 0, rows.size(), Math.max((long)size, total));
        }

    }

    public PageInfo(List<T> rows) {
        this();
        this.calc(rows);
    }

    private void calc(List<T> rows) {
        if (rows == null) {
            this.calc(rows, 0, 0, 0L);
        } else if (rows instanceof Page) {
            this.calc(rows, (int) ((Page)rows).getStartRow(), (int) ((Page)rows).getEndRow(), ((Page)rows).getTotal());
        } else {
            int size = rows.size();
            long total = this.getLong("total", (long)size + 0L);
            this.calc(rows, 0, size, total);
        }

    }

    private void calc(List<T> rows, int start, int end, long total) {
        if (rows == null) {
            this.put("total", 0);
            this.put("rows", new ArrayList());
            this.put("firstPage", true);
            this.put("lastPage", true);
            this.put("pageNo", 1);
        } else {
            this.put("rows", rows);
            this.put("total", total);
            if (start <= 0) {
                this.put("pageNo", 1);
                this.put("firstPage", true);
            }

            if ((long)end >= total - 1L) {
                this.put("lastPage", true);
                int size = end - start;
                int pageNo = Double.valueOf(Math.ceil((double)total * 1.0D / (double)size)).intValue();
                this.put("pageNo", pageNo);
            }
        }

    }

    public long getTotal() {
        return this.getLong("total", 0L);
    }

    public void setTotal(long total) {
        this.put("total", total);
    }

    public List<T> getRows() {
        return (List)this.get("rows");
    }

    public void setRows(List<T> rows) {
        this.calc(rows);
    }

    public boolean isFirstPage() {
        return (Boolean)this.get("firstPage");
    }

    public void setFirstPage(boolean firstPage) {
        this.put("firstPage", firstPage);
    }

    public boolean isLastPage() {
        return (Boolean)this.get("lastPage");
    }

    public void setLastPage(boolean lastPage) {
        this.put("lastPage", lastPage);
    }
}
