package com.deng.user.service;

import com.dora.common.db.service.BaseService;
import com.deng.user.bean.RolePower;
import com.deng.user.dao.RolePowerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 业务处理
 * @author conta
 * @create 2021-09-28 16:56:01
 */

@Service
public class RolePowerService extends BaseService<RolePower> {

    @Autowired
    private RolePowerDao rolePowerDao;


}
