package com.deng.user.service;

import com.dora.common.db.service.BaseService;
import com.deng.user.bean.PageElement;
import com.deng.user.dao.PageElementDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 业务处理
 * @author conta
 * @create 2021-09-28 16:56:01
 */

@Service
public class PageElementService extends BaseService<PageElement> {

    @Autowired
    private PageElementDao pageElementDao;


}
