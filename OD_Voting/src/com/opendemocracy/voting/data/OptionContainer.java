package com.opendemocracy.voting.data;

import java.io.Serializable;

import com.vaadin.data.util.BeanItemContainer;

public class OptionContainer extends BeanItemContainer<Option> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8523151211034009406L;

	public OptionContainer() throws InstantiationException,
			IllegalAccessException {
		super(Option.class);
	}

	public static OptionContainer createWithTestData() {
		OptionContainer c = null;
		try {
			c = new OptionContainer();
			for (int i = 0; i < 100; i++) {
				Option p = new Option();
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
