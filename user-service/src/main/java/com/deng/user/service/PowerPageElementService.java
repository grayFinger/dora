package com.deng.user.service;

import com.dora.common.db.service.BaseService;
import com.deng.user.bean.PowerPageElement;
import com.deng.user.dao.PowerPageElementDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 业务处理
 * @author conta
 * @create 2021-09-28 16:56:01
 */

@Service
public class PowerPageElementService extends BaseService<PowerPageElement> {

    @Autowired
    private PowerPageElementDao powerPageElementDao;


}
