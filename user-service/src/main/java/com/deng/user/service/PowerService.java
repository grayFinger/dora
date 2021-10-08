package com.deng.user.service;

import com.dora.common.db.service.BaseService;
import com.deng.user.bean.Power;
import com.deng.user.dao.PowerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 业务处理
 * @author conta
 * @create 2021-09-28 16:56:01
 */

@Service
public class PowerService extends BaseService<Power> {

    @Autowired
    private PowerDao powerDao;


}
