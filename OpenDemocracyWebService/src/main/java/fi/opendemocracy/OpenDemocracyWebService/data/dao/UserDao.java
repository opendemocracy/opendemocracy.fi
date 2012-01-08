package fi.opendemocracy.OpenDemocracyWebService.data.dao;

import fi.opendemocracy.OpenDemocracyWebService.data.model.User;

public interface UserDao {

	public void save(User user);

	public void update(User user);

	public void delete(User user);

	public User findByUserName(String userName);
}
