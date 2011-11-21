package com.opendemocracy.voting.data;
import java.io.Serializable;

import com.vaadin.data.util.BeanItemContainer;

public class ExpertContainer extends BeanItemContainer<Expert> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8523151211034009406L;

	public ExpertContainer() throws InstantiationException, IllegalAccessException {
		super(Expert.class);
	}
	
}
