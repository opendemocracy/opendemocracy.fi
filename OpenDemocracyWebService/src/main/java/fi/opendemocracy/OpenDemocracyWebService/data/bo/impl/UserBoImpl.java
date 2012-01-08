package fi.opendemocracy.OpenDemocracyWebService.data.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.opendemocracy.OpenDemocracyWebService.data.bo.UserBo;
import fi.opendemocracy.OpenDemocracyWebService.data.dao.UserDao;
import fi.opendemocracy.OpenDemocracyWebService.data.model.User;

@Service("userBo")
public class UserBoImpl implements UserBo{

	@Autowired
	UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void save(User user) {
		userDao.save(user);
	}

	public void update(User user) {
		userDao.update(user);
	}

	public void delete(User user) {
		userDao.delete(user);
	}

	public User findByUserName(String userName) {
		return userDao.findByUserName(userName);
	}

}
