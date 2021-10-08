package com.deng.user.service;

import com.dora.common.db.service.BaseService;
import com.deng.user.bean.GroupRole;
import com.deng.user.dao.GroupRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

/**
 * 业务处理
 * @author conta
 * @create 2021-09-28 16:56:01
 */

@Service
public class GroupRoleService extends BaseService<GroupRole> {

    @Autowired
    private GroupRoleDao groupRoleDao;
}
