/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.sys.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.sys.entity.MemUser;

/**
 * 会员用户DAO接口
 * @author ThinkGem
 * @version 2020-9-19
 */
@MyBatisDao
public interface MemUserDao extends CrudDao<MemUser> {

}
