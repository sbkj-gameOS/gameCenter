package com.bradypod.web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 描述：房卡充值记录
 * 创建人：田帅
 */
@Entity
@Table(name = "bm_room_recharge_record")
@org.hibernate.annotations.Proxy(lazy = false)
public class RoomRechargeRecord implements java.io.Serializable{
    @Id
    private Integer id;

    @Column(name="user_name")
    private String userName;

    @Column(name="invitation_code")
    private String invitationCode;

    @Column(name="recharge_time")
    private Date rechargeTime;

    @Column(name="room_count")
    private Integer roomCount;

    @Column(name="original_price")
    private BigDecimal originalPrice;

    @Column(name="preferential_amount")
    private BigDecimal preferentialAmount;

    @Column(name="pay_amount")
    private BigDecimal payAmount;

    @Column(name="directly_the_last_amount")
    private BigDecimal directlyTheLastAmount;

    @Column(name="indirect_the_last_amount")
    private BigDecimal indirectTheLastAmount;

    @Column(name="create_time")
    private Date createTime;

    @Column(name="is_Del")
    private Integer isDel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode == null ? null : invitationCode.trim();
    }

    public Date getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(Date rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public Integer getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(Integer roomCount) {
        this.roomCount = roomCount;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getPreferentialAmount() {
        return preferentialAmount;
    }

    public void setPreferentialAmount(BigDecimal preferentialAmount) {
        this.preferentialAmount = preferentialAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getDirectlyTheLastAmount() {
        return directlyTheLastAmount;
    }

    public void setDirectlyTheLastAmount(BigDecimal directlyTheLastAmount) {
        this.directlyTheLastAmount = directlyTheLastAmount;
    }

    public BigDecimal getIndirectTheLastAmount() {
        return indirectTheLastAmount;
    }

    public void setIndirectTheLastAmount(BigDecimal indirectTheLastAmount) {
        this.indirectTheLastAmount = indirectTheLastAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}