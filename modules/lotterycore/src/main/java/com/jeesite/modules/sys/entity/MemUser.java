/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.jeesite.common.mybatis.annotation.Table;

import javax.validation.Valid;

/**
 * 会员用户Entity
 * @author ThinkGem
 * @version 2020-9-19
 */
@Table(name="${_prefix}sys_user", alias="a", columns={
        @Column(includeEntity=User.class),
}, joinTable={
        @JoinTable(type=Type.JOIN, entity=Member.class, alias="e",
                on="e.mem_id=a.ref_code AND a.user_type=#{USER_TYPE_MEMBER}", columns = {
                @Column(includeEntity=Member.class),
        }),
},
        orderBy="a.user_weight DESC, a.update_date DESC"
)
public class MemUser extends User {

    private static final long serialVersionUID = 1L;

    public MemUser() {
        this(null);
    }

    public MemUser(String id){
        super(id);
    }

    @Valid
    public Member getMember(){
        Member member = (Member)super.getRefObj();
        if (member == null){
            member = new Member(getRefCode());
            super.setRefObj(member);
        }
        return member;
    }

    public void setMember(Member Member){
        super.setRefObj(Member);
    }

    @Override
    @JsonIgnore
    public <E> E getRefObj() {
        return super.getRefObj();
    }

}
