package com.deng.user.service;

import com.dora.common.db.service.BaseService;
import com.deng.user.bean.PowerOperation;
import com.deng.user.dao.PowerOperationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 业务处理
 * @author conta
 * @create 2021-09-28 16:56:01
 */

@Service
public class PowerOperationService extends BaseService<PowerOperation> {

    @Autowired
    private PowerOperationDao powerOperationDao;


}
