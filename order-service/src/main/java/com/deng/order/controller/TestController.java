package com.deng.order.controller;

import com.deng.order.bean.Order;
import com.deng.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/saveTest")
    public Order insetTest(@RequestBody Order order){
        orderService.insert(order);
        return order;
    }

    @GetMapping("/selectTest")
    public Order selectTest(Long id){
        return orderService.findById(id);
    }
}
