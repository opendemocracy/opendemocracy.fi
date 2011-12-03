package com.opendemocracy.voting.data;

import java.io.Serializable;

import com.vaadin.data.util.BeanItemContainer;

public class VoteContainer extends BeanItemContainer<Vote> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8523151211034009406L;

	public VoteContainer() throws InstantiationException,
			IllegalAccessException {
		super(Vote.class);
	}

	public static VoteContainer createWithTestData() {
		VoteContainer c = null;
		try {
			c = new VoteContainer();
			for (int i = 0; i < 100; i++) {
				Vote p = new Vote();
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
