package fi.opendemocracy.OpenDemocracyWebService.data.bo;

import fi.opendemocracy.OpenDemocracyWebService.data.model.User;

public interface UserBo {

	public void save(User user);

	public void update(User user);

	public void delete(User user);

	public User findByUserName(String userName);
}
