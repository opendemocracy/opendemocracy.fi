package com.opendemocracy.voting.data;
import java.io.Serializable;
import java.util.ArrayList;

import com.vaadin.data.util.BeanItemContainer;

public class CategoryContainer extends BeanItemContainer<Category> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8523151211034009406L;

	
	
	public CategoryContainer() throws InstantiationException, IllegalAccessException {
		super(Category.class);
	}

	public static CategoryContainer createWithTestData() {
		CategoryContainer c = null;
		try {
			c = new CategoryContainer();
			for (int i = 0; i < 100; i++) {
				Category p = new Category();
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
