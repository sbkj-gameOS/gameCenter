package com.bradypod.web.service.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bradypod.web.model.PlayUser;

public abstract interface PlayUserRepository extends JpaRepository<PlayUser, String> {

	public abstract PlayUser findById(String paramString);

	public abstract PlayUser findByUsername(String username);

	public abstract PlayUser findByEmail(String email);

	public abstract PlayUser findByUsernameAndPassword(String username, String password);

	public abstract Page<PlayUser> findByDatastatus(boolean datastatus, String orgi, Pageable paramPageable);

	public abstract Page<PlayUser> findByDatastatusAndUsername(boolean datastatus, String orgi, String username, Pageable paramPageable);

	/**
	 * @Title: findByOpenid
	 * @Description: TODO(通过openid查询用户信息)
	 * @param openid
	 * @return 设定文件 PlayUser 返回类型
	 */
	public abstract PlayUser findByOpenid(String openid);
}
