package com.deng.user.service;

import com.dora.common.db.service.BaseService;
import com.deng.user.bean.PowerFile;
import com.deng.user.dao.PowerFileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 业务处理
 * @author conta
 * @create 2021-09-28 16:56:01
 */

@Service
public class PowerFileService extends BaseService<PowerFile> {

    @Autowired
    private PowerFileDao powerFileDao;


}
