package com.deng.order.service;

import com.dora.common.db.service.BaseService;
import com.deng.order.bean.OrderAppraise;
import com.deng.order.dao.OrderAppraiseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 业务处理
 * @author conta
 * @create 2021-09-28 15:56:35
 */

@Service
public class OrderAppraiseService extends BaseService<OrderAppraise> {

    @Autowired
    private OrderAppraiseDao orderAppraiseDao;


}
