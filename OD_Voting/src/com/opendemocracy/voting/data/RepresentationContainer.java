package com.opendemocracy.voting.data;
import java.io.Serializable;

import com.vaadin.data.util.BeanItemContainer;

public class RepresentationContainer extends BeanItemContainer<Representation> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8523151211034009406L;

	public RepresentationContainer() throws InstantiationException, IllegalAccessException {
		super(Representation.class);
	}
	
}
