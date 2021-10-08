package com.deng.order.service;

import com.dora.common.db.service.BaseService;
import com.deng.order.bean.SkuAttributeKey;
import com.deng.order.dao.SkuAttributeKeyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 业务处理
 * @author conta
 * @create 2021-09-28 15:56:35
 */

@Service
public class SkuAttributeKeyService extends BaseService<SkuAttributeKey> {

    @Autowired
    private SkuAttributeKeyDao skuAttributeKeyDao;


}
