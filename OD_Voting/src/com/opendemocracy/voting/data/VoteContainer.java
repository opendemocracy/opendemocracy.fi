package com.opendemocracy.voting.data;
import java.io.Serializable;

import com.vaadin.data.util.BeanItemContainer;

public class VoteContainer extends BeanItemContainer<Vote> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8523151211034009406L;

	public VoteContainer() throws InstantiationException, IllegalAccessException {
		super(Vote.class);
	}
	
}
