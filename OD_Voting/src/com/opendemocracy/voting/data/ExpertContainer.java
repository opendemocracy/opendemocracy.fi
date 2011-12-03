package com.opendemocracy.voting.data;
import java.io.Serializable;
import java.util.ArrayList;

import com.vaadin.data.util.BeanItemContainer;

public class ExpertContainer extends BeanItemContainer<Expert> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8523151211034009406L;

	public ExpertContainer() throws InstantiationException, IllegalAccessException {
		super(Expert.class);
	}

	public static ExpertContainer createWithTestData() {
		ExpertContainer c = null;
		try {
			c = new ExpertContainer();
			for (int i = 0; i < 100; i++) {
				Expert p = new Expert();
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
