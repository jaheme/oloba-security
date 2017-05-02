package com.oloba.module.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oloba.core.utils.DigestUtil;
import com.oloba.module.common.LoginedService;
import com.oloba.module.common.TResult;
import com.oloba.module.user.model.UserForPrivilege;
import com.oloba.module.user.pojo.TBaseUser;
import com.oloba.security.MyUserDetails;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public MyUserDetails loginWith(String username) {
		TBaseUser user = userDao.getUserByUsername(username);
		if (null == user) {
			return null;
		}
		return new MyUserDetails(user.getId(), 
				username, 
				user.getPassword().toLowerCase(), 
				user.isEnabled());
	}
	
	
	protected TBaseUser getUserById(int userid) {
		return userDao.getUserById(userid);
	}
	
	/**
	 * 用户建立的用户列表
	 * @param create_uid
	 * @return
	 */
	public List<UserForPrivilege> getMyUserList(int create_uid) {
		List<TBaseUser> list = userDao.getMyUserList(create_uid);
		List<UserForPrivilege> uplist = new ArrayList<>(list.size());
		for (TBaseUser user : list) {
			uplist.add(new UserForPrivilege(user));
		}
		return uplist;
	}
	
	
	protected TResult<Boolean> newUser(TBaseUser user) {
		int userid = userDao.getUserId(user.getUsername());
		if (userid > 0) {
			return TResult.valueOf(user.getUsername()+"重复");
		}
		userid = LoginedService.getUserid();
		TBaseUser create_user = userDao.getUserById(userid);
		user.setCreate_uid(userid);
		user.setCompany_id(create_user.getCompany_id());
		user.setPassword(DigestUtil.md5Hex(user.getPassword()));
		return TResult.valueT(userDao.newUser(user));
	}
	
	protected TResult<Boolean> updateUser(TBaseUser user) {
		int userid = userDao.getUserId(user.getUsername());
		if (userid > 0 && userid != user.getId()) {
			return TResult.valueOf(user.getUsername()+"重复");
		}
		userid = LoginedService.getUserid();
		TBaseUser create_user = userDao.getUserById(userid);
		user.setCreate_uid(userid);
		user.setCompany_id(create_user.getCompany_id());
		return TResult.valueT(userDao.updateUser(user));
	}
}
