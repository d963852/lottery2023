package com.jeesite.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.BaseEntity;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.entity.TreeEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 乐透会员管理Entity
 *
 * @author tg
 * @version 2023-06-10
 */
@Table(name = "${_prefix}sys_member", alias = "a", label = "会员信息", columns = {
        @Column(name = "mem_id", attrName = "memId", label = "会员id", isPK = true),
        @Column(name = "mem_name", attrName = "memName", label = "会员名", queryType = QueryType.LIKE, isTreeName = true),
        @Column(name = "balance", attrName = "balance", label = "余额", isUpdateForce = true),
        @Column(name = "lately_login_time", attrName = "latelyLoginTime", label = "最近登录时间", isUpdateForce = true),
        @Column(name = "login_pwd", attrName = "loginPwd", label = "登录密码", isQuery = false),
        @Column(name = "money_pwd", attrName = "moneyPwd", label = "资金密码", isQuery = false),
        @Column(name = "real_name", attrName = "realName", label = "真实姓名", isQuery = false),
        @Column(name = "registered_time", attrName = "registeredTime", label = "注册时间", isUpdateForce = true),
        @Column(name = "version", attrName = "version", label = "乐观锁版本号", isQuery = false, isUpdateForce = true),
        @Column(name = "account_type", attrName = "accountType", label = "账号类型", isQuery = false),
        @Column(name = "account_level", attrName = "accountLevel", label = "账号级别", isUpdateForce = true),
        @Column(name = "rebate", attrName = "rebate", label = "返点", isUpdateForce = true),
        @Column(name = "odds", attrName = "odds", label = "赔率", isUpdateForce = true),
        @Column(name = "wage", attrName = "wage", label = "工资", isUpdateForce = true),
        @Column(name = "bonus", attrName = "bonus", label = "分红", isUpdateForce = true),
        @Column(name = "biz_status", attrName = "bizStatus", label = "业务状态", isQuery = false),
        @Column(includeEntity = DataEntity.class),
        @Column(includeEntity = BaseEntity.class),
        @Column(name = "deleted", attrName = "deleted", label = "是否删除", isQuery = false),
        @Column(name = "tenant_id", attrName = "tenantId", label = "租户编号", isQuery = false),
        @Column(includeEntity = TreeEntity.class),
}, orderBy = "a.tree_sorts, a.mem_id"
)
public class Member extends TreeEntity<Member> {

    private static final long serialVersionUID = 1L;
    private String memId;        // 会员id
    private String memName;        // 会员名
    private Double balance;        // 余额
    private Date latelyLoginTime;        // 最近登录时间
    private String loginPwd;        // 登录密码
    private String moneyPwd;        // 资金密码
    private String realName;        // 真实姓名
    private Date registeredTime;        // 注册时间
    private Long version;        // 乐观锁版本号
    private String accountType;        // 账号类型
    private Long accountLevel;        // 账号级别
    private Double rebate;        // 投注返点
    private Double odds;        // 赔率
    private Double wage;        // 工资
    private Double bonus;        // 分红
    private String bizStatus;        // 业务状态
    private String deleted;        // 是否删除
    private String tenantId;        // 租户编号

    public Member() {
        this(null);
    }

    public Member(String id) {
        super(id);
    }

    @Override
    public Member getParent() {
        return parent;
    }

    @Override
    public void setParent(Member parent) {
        this.parent = parent;
    }

    public String getMemId() {
        return memId;
    }

    public void setMemId(String memId) {
        this.memId = memId;
    }

    @Size(min = 0, max = 64, message = "会员名长度不能超过 64 个字符")
    public String getMemName() {
        return memName;
    }

    public void setMemName(String memName) {
        this.memName = memName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getLatelyLoginTime() {
        return latelyLoginTime;
    }

    public void setLatelyLoginTime(Date latelyLoginTime) {
        this.latelyLoginTime = latelyLoginTime;
    }

    @Size(min = 0, max = 64, message = "登录密码长度不能超过 64 个字符")
    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    @Size(min = 0, max = 64, message = "资金密码长度不能超过 64 个字符")
    public String getMoneyPwd() {
        return moneyPwd;
    }

    public void setMoneyPwd(String moneyPwd) {
        this.moneyPwd = moneyPwd;
    }

    @Size(min = 0, max = 64, message = "真实姓名长度不能超过 64 个字符")
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getRegisteredTime() {
        return registeredTime;
    }

    public void setRegisteredTime(Date registeredTime) {
        this.registeredTime = registeredTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Size(min = 0, max = 10, message = "账号类型长度不能超过 10 个字符")
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Long getAccountLevel() {
        return accountLevel;
    }

    public void setAccountLevel(Long accountLevel) {
        this.accountLevel = accountLevel;
    }

    public Double getRebate() {
        return rebate;
    }

    public void setRebate(Double rebate) {
        this.rebate = rebate;
    }

    public Double getOdds() {
        return odds;
    }

    public void setOdds(Double odds) {
        this.odds = odds;
    }

    public Double getWage() {
        return wage;
    }

    public void setWage(Double wage) {
        this.wage = wage;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    @Size(min = 0, max = 64, message = "业务状态长度不能超过 64 个字符")
    public String getBizStatus() {
        return bizStatus;
    }

    public void setBizStatus(String bizStatus) {
        this.bizStatus = bizStatus;
    }

    @Size(min = 0, max = 64, message = "是否删除长度不能超过 64 个字符")
    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    @Size(min = 0, max = 64, message = "租户编号长度不能超过 64 个字符")
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Double getBalance_gte() {
        return sqlMap.getWhere().getValue("balance", QueryType.GTE);
    }

    public void setBalance_gte(Double balance) {
        sqlMap.getWhere().and("balance", QueryType.GTE, balance);
    }

    public Double getBalance_lte() {
        return sqlMap.getWhere().getValue("balance", QueryType.LTE);
    }

    public void setBalance_lte(Double balance) {
        sqlMap.getWhere().and("balance", QueryType.LTE, balance);
    }

    public Date getLatelyLoginTime_gte() {
        return sqlMap.getWhere().getValue("lately_login_time", QueryType.GTE);
    }

    public void setLatelyLoginTime_gte(Date latelyLoginTime) {
        sqlMap.getWhere().and("lately_login_time", QueryType.GTE, latelyLoginTime);
    }

    public Date getLatelyLoginTime_lte() {
        return sqlMap.getWhere().getValue("lately_login_time", QueryType.LTE);
    }

    public void setLatelyLoginTime_lte(Date latelyLoginTime) {
        sqlMap.getWhere().and("lately_login_time", QueryType.LTE, latelyLoginTime);
    }

    public Date getRegisteredTime_gte() {
        return sqlMap.getWhere().getValue("registered_time", QueryType.GTE);
    }

    public void setRegisteredTime_gte(Date registeredTime) {
        sqlMap.getWhere().and("registered_time", QueryType.GTE, registeredTime);
    }

    public Date getRegisteredTime_lte() {
        return sqlMap.getWhere().getValue("registered_time", QueryType.LTE);
    }

    public void setRegisteredTime_lte(Date registeredTime) {
        sqlMap.getWhere().and("registered_time", QueryType.LTE, registeredTime);
    }

    public Double getRebate_gte() {
        return sqlMap.getWhere().getValue("rebate", QueryType.GTE);
    }

    public void setRebate_gte(Double rebate) {
        sqlMap.getWhere().and("rebate", QueryType.GTE, rebate);
    }

    public Double getRebate_lte() {
        return sqlMap.getWhere().getValue("rebate", QueryType.LTE);
    }

    public void setRebate_lte(Double rebate) {
        sqlMap.getWhere().and("rebate", QueryType.LTE, rebate);
    }

    public Double getOdds_gte() {
        return sqlMap.getWhere().getValue("odds", QueryType.GTE);
    }

    public void setOdds_gte(Double odds) {
        sqlMap.getWhere().and("odds", QueryType.GTE, odds);
    }

    public Double getOdds_lte() {
        return sqlMap.getWhere().getValue("odds", QueryType.LTE);
    }

    public void setOdds_lte(Double odds) {
        sqlMap.getWhere().and("odds", QueryType.LTE, odds);
    }

    public Double getWage_gte() {
        return sqlMap.getWhere().getValue("wage", QueryType.GTE);
    }

    public void setWage_gte(Double wage) {
        sqlMap.getWhere().and("wage", QueryType.GTE, wage);
    }

    public Double getWage_lte() {
        return sqlMap.getWhere().getValue("wage", QueryType.LTE);
    }

    public void setWage_lte(Double wage) {
        sqlMap.getWhere().and("wage", QueryType.LTE, wage);
    }

    public Double getBonus_gte() {
        return sqlMap.getWhere().getValue("bonus", QueryType.GTE);
    }

    public void setBonus_gte(Double bonus) {
        sqlMap.getWhere().and("bonus", QueryType.GTE, bonus);
    }

    public Double getBonus_lte() {
        return sqlMap.getWhere().getValue("bonus", QueryType.LTE);
    }

    public void setBonus_lte(Double bonus) {
        sqlMap.getWhere().and("bonus", QueryType.LTE, bonus);
    }

    public String getParentName() {
        if (this.getParent() != null) {
            return this.getParent().getMemName();
        } else {
            return "";
        }
    }

}