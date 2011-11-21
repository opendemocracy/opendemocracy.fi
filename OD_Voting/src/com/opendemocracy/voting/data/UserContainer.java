package com.opendemocracy.voting.data;
import java.io.Serializable;

import com.vaadin.data.util.BeanItemContainer;

public class UserContainer extends BeanItemContainer<User> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8523151211034009406L;

	public UserContainer() throws InstantiationException, IllegalAccessException {
		super(User.class);
	}
	
}
