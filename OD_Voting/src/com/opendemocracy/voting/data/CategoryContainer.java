package com.opendemocracy.voting.data;
import java.io.Serializable;

import com.vaadin.data.util.BeanItemContainer;

public class CategoryContainer extends BeanItemContainer<Category> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8523151211034009406L;

	
	
	public CategoryContainer() throws InstantiationException, IllegalAccessException {
		super(Category.class);
	}
	
}
