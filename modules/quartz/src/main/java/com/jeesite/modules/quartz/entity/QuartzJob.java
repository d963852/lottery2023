/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.quartz.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * Quartz定时任务表Entity
 * @author 淡梦如烟
 * @version 2020-03-02
 */
@Table(name="quartz_job", alias="a", columns={
		@Column(name="id", attrName="id", label="主键", isPK=true),
		@Column(name="job_class_name", attrName="jobClassName", label="任务类名", queryType=QueryType.LIKE),
		@Column(name="description", attrName="description", label="描述", queryType=QueryType.LIKE),
		@Column(name="status", attrName="status", label="状态", isUpdate=false),
		@Column(name="cron_expression", attrName="cronExpression", label="cron表达式"),
		@Column(name="parameter", attrName="parameter", label="参数"),
		@Column(name="create_by", attrName="createBy", label="创建人", isUpdate=false, isQuery=false),
		@Column(name="create_time", attrName="createTime", label="创建时间"),
		@Column(name="update_by", attrName="updateBy", label="修改人", isQuery=false),
		@Column(name="del_flag", attrName="delFlag", label="删除状态"),
		@Column(name="update_time", attrName="updateTime", label="修改时间"),
	}, orderBy="a.id DESC"
)
public class QuartzJob extends DataEntity<QuartzJob> {
	
	private static final long serialVersionUID = 1L;
	private String jobClassName;		// 任务类名
	private String description;		// 描述
	private String cronExpression;		// cron表达式
	private String parameter;		// 参数
	private Date createTime;		// 创建时间
	private String delFlag;		// 删除状态
	private Date updateTime;		// 修改时间
	private String status;		// 状态

	public QuartzJob() {
		this(null);
	}

	public QuartzJob(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="任务类名长度不能超过 255 个字符")
	public String getJobClassName() {
		return jobClassName;
	}

	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}
	
	@Length(min=0, max=255, message="描述长度不能超过 255 个字符")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=255, message="cron表达式长度不能超过 255 个字符")
	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	
	@Length(min=0, max=255, message="参数长度不能超过 255 个字符")
	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=10, message="删除状态长度不能超过 10 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Length(min=0, max=10, message="状态长度不能超过 10 个字符")
	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public void setStatus(String status) {
		this.status = status;
	}
}