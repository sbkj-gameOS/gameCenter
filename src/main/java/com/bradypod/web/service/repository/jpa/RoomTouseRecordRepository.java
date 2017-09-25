package com.bradypod.web.service.repository.jpa;

import com.bradypod.web.model.RoomTouseRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 田帅 on 2017/9/25/025.
 */
public abstract interface RoomTouseRecordRepository extends JpaRepository<RoomTouseRecord, String> {

    /**]
     * 根据用户名邀请码查询房卡使用记录
     * @param userName
     * @param invitationCode
     * @param page
     * @return
     */
    public abstract Page<RoomTouseRecord> findByUserNameAndInvitationCode(String userName, String invitationCode, Pageable page);
}
