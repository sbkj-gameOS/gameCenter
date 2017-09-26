package com.bradypod.web.service.repository.jpa;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bradypod.web.model.PlayUser;

public abstract interface PlayUserRepository extends JpaRepository<PlayUser, String> {

	public abstract PlayUser findById(String paramString);

	public abstract PlayUser findByUsername(String username);

	public abstract PlayUser findByEmail(String email);

	public abstract PlayUser findByUsernameAndPassword(String username, String password);

	public abstract Page<PlayUser> findByDatastatus(boolean datastatus, String orgi, Pageable paramPageable);

	public abstract Page<PlayUser> findByDatastatusAndUsername(boolean datastatus, String orgi, String username, Pageable paramPageable);

	public abstract PlayUser findByOpenid(String openid);

	public abstract Page<PlayUser> findByNicknameLike(String username, Pageable pageable);

	@Query(value = "update bm_playuser set pinvitationcode = :pinvitationcode,UPDATETIME = now() where id = :id", nativeQuery = true)
	@Modifying
	@Transactional
	public abstract void setPinvitationcodeById(@Param("pinvitationcode") String pinvitationcode, @Param("id") String id);

	public abstract PlayUser findByInvitationcode(String pinvitationcode);

	public abstract List<PlayUser> findByPinvitationcode(String invitationcode);
}
