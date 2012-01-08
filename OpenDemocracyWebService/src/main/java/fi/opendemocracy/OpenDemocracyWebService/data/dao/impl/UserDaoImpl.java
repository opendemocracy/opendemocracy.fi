package fi.opendemocracy.OpenDemocracyWebService.data.dao.impl;

import java.util.List;

import fi.opendemocracy.OpenDemocracyWebService.data.dao.UserDao;
import fi.opendemocracy.OpenDemocracyWebService.data.model.User;
import fi.opendemocracy.OpenDemocracyWebService.hibernate.util.CustomHibernateDaoSupport;

public class UserDaoImpl extends CustomHibernateDaoSupport implements UserDao {

	public void save(User user) {
		getHibernateTemplate().save(user);

	}

	public void update(User user) {
		getHibernateTemplate().update(user);
	}

	public void delete(User user) {
		getHibernateTemplate().delete(user);
	}

	public User findByUserName(String userName) {
		List list = getHibernateTemplate().find("from User where userName=?",
				userName);
		return (User) list.get(0);
	}

}
