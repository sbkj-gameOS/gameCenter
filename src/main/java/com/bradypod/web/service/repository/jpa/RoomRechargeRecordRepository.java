package com.bradypod.web.service.repository.jpa;

import com.bradypod.web.model.RoomRechargeRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by 田帅 on 2017/9/25/025.
 */
public abstract interface RoomRechargeRecordRepository extends JpaRepository<RoomRechargeRecord, String> {

    /**]
     * 根据用户名邀请码查询房卡充值记录
     * @param userName
     * @param invitationCode
     * @return
     */
    public abstract Page<RoomRechargeRecord> findByUserNameLikeOrInvitationCodeLike(String userName, String invitationCode, Pageable Pageable);
}
