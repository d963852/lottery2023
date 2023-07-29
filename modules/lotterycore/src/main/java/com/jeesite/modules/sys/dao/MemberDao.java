package com.jeesite.modules.sys.dao;

import com.jeesite.common.dao.TreeDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.sys.entity.Member;

/**
 * 乐透会员管理DAO接口
 * @author tg
 * @version 2023-06-10
 */
@MyBatisDao
public interface MemberDao extends TreeDao<Member> {
	
}