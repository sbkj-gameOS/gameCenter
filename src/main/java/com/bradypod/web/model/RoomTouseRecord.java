package com.bradypod.web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
/**
 * 描述：房卡使用记录
 * 创建人：田帅
 */
@Entity
@Table(name = "bm_room_touse_record")
@org.hibernate.annotations.Proxy(lazy = false)
public class RoomTouseRecord implements java.io.Serializable{
    @Id
    private Integer id;

    @Column(name="user_name")
    private String userName;

    @Column(name="invitation_code")
    private String invitationCode;

    @Column(name="use_time")
    private Date useTime;

    @Column(name="use_room_count")
    private Integer useRoomCount;

    @Column(name="surplus_room_count")
    private Integer surplusRoomCount;

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

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    public Integer getUseRoomCount() {
        return useRoomCount;
    }

    public void setUseRoomCount(Integer useRoomCount) {
        this.useRoomCount = useRoomCount;
    }

    public Integer getSurplusRoomCount() {
        return surplusRoomCount;
    }

    public void setSurplusRoomCount(Integer surplusRoomCount) {
        this.surplusRoomCount = surplusRoomCount;
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