package com.opendemocracy.voting.data;
import java.io.Serializable;

import com.vaadin.data.util.BeanItemContainer;

public class OptionContainer extends BeanItemContainer<Option> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8523151211034009406L;

	public OptionContainer() throws InstantiationException, IllegalAccessException {
		super(Option.class);
	}
	
}
