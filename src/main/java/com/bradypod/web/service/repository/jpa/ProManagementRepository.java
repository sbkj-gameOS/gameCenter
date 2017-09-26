package com.bradypod.web.service.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.bradypod.web.model.PlayUser;
import com.bradypod.web.model.ProManagement;

/**
 * @ClassName: ProManagementRepository
 * @Description: TODO(分润管理)
 * @author dave
 * @date 2017年9月25日 下午8:03:31
 */
public interface ProManagementRepository extends JpaRepository<ProManagement, String> {

	/**
	 * @Title: findByUserNameAndInvitationCode
	 * @Description: TODO()
	 * @param userName
	 * @param invitationCode
	 * @return 设定文件 List<ProManagement> 返回类型
	 */
	//@Query(value = "select * from bm_pro_management pm where pm.USER_NAME like CONCAT('%',:userName,'%') and pm.INVITATION_CODE like CONCAT('%',:invitationCode,'%') limit :pageNo,20",nativeQuery = true)
	//public List<ProManagement> findByUserNameAndInvitationCode(@Param("userName")String userName, @Param("invitationCode")String invitationCode, @Param("pageNo")Integer pageNo);
	
	public abstract Page<ProManagement> findByUserNameAndInvitationCode(String username, String invitationCode,Pageable paramPageable);
}
