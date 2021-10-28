package com.gaoap.opf.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 微服务中，系统编号管理。方便授权
 * </p>
 *
 * @author gaoyd
 * @since 2021-10-27
 */
@TableName("sys_subsystem")
public class SysSubsystem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系统编号
     */
      private String subId;

    /**
     * 系统名称
     */
    private String subName;

    /**
     * 系统备注
     */
    private String remark;

    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;

    /**
     * 最后更新人
     */
    private String lastUpdateBy;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除 -1：已删除 0：正常
     */
    private Integer delFlag;


    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "SysSubsystem{" +
        "subId=" + subId +
        ", subName=" + subName +
        ", remark=" + remark +
        ", lastUpdateTime=" + lastUpdateTime +
        ", lastUpdateBy=" + lastUpdateBy +
        ", createBy=" + createBy +
        ", createTime=" + createTime +
        ", delFlag=" + delFlag +
        "}";
    }
}
