package com.opendemocracy.voting.data;

import java.io.Serializable;
import java.util.Locale;

import com.vaadin.data.util.BeanItemContainer;

public class CategoryContainer extends BeanItemContainer<Category> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8523151211034009406L;

	/**
	 * Natural property order for Person bean. Used in tables and forms.
	 */
	public final static Object[] NATURAL_COL_ORDER = new Object[] { "id",
			"name", "description", "language" };

	/**
	 * "Human readable" captions for properties in same order as in
	 * NATURAL_COL_ORDER.
	 */
	public final static String[] COL_HEADERS_ENGLISH = new String[] { "Id",
			"Name", "Description", "Locale/language" };

	public CategoryContainer() throws InstantiationException,
			IllegalAccessException {
		super(Category.class);
	}

	public static CategoryContainer createWithTestData() {
		CategoryContainer c = null;
		try {
			c = new CategoryContainer();
			for (int i = 0; i < 100; i++) {
				Category p = new Category(i, "CategoryName: #" + i, "CategoryDescription: #" + i, Locale.ENGLISH);
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
