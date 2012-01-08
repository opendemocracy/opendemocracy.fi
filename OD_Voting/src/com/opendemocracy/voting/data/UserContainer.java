package com.opendemocracy.voting.data;

import java.io.Serializable;

import com.vaadin.data.util.BeanItemContainer;

public class UserContainer extends BeanItemContainer<User> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8523151211034009406L;

	public UserContainer() throws InstantiationException,
			IllegalAccessException {
		super(User.class);
	}

	public static UserContainer createWithTestData() {
		UserContainer c = null;
		try {
			c = new UserContainer();
			for (int i = 0; i < 100; i++) {
				User p = new User(i, null, null);
				c.addItem(p);
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return c;
	}
}
