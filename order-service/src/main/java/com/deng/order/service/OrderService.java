package com.deng.order.service;

import com.dora.common.db.service.BaseService;
import com.deng.order.bean.Order;
import com.deng.order.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 业务处理
 * @author conta
 * @create 2021-09-28 15:56:35
 */

@Service
public class OrderService extends BaseService<Order> {

    @Autowired
    private OrderDao orderDao;


}
