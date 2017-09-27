package com.bradypod.web.service.repository.jpa;

import com.bradypod.web.model.RoomRechargeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by 田帅 on 2017/9/25/025.
 */
public abstract interface RoomRechargeRecordRepository extends JpaRepository<RoomRechargeRecord, String> {

    /**
     * 查询总条数
     * @param userName
     * @param invitationCode
     * @return
     */
    @Query(value = "select count(*) from bm_room_recharge_record where USER_NAME LIKE  CONCAT('%',:userName,'%') and INVITATION_CODE LIKE CONCAT('%',:invitationCode,'%') ",nativeQuery = true)
    public abstract  int findRechargeCount(@Param("userName") String userName, @Param("invitationCode") String invitationCode);

    /**]
     * 根据用户名邀请码查询房卡充值记录
     * @param userName
     * @param invitationCode
     * @return
     */
    @Query(value = "select * from bm_room_recharge_record where USER_NAME LIKE  CONCAT('%',:userName,'%') and INVITATION_CODE LIKE CONCAT('%',:invitationCode,'%')  limit :startData,:limit",nativeQuery = true)
    public abstract List<RoomRechargeRecord> findByUserNameLikeOrInvitationCodeLike(@Param("userName") String userName, @Param("invitationCode") String invitationCode, @Param("startData")Integer startData, @Param("limit")Integer limit);
}
