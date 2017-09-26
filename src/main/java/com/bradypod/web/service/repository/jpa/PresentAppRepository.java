package com.bradypod.web.service.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bradypod.web.model.PresentApp;

/**
 * @ClassName: PresentAppRepository
 * @Description: TODO(提现记录表)
 * @author dave
 * @date 2017年9月25日 下午8:02:17
 */
public interface PresentAppRepository extends JpaRepository<PresentApp, String>{

	public Page<PresentApp> findByUserNameLikeAndInvitationCodeLike(String userName, String invitationCode,Pageable pageable);
}
