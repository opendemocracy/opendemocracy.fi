package com.opendemocracy.voting.data;

import java.io.Serializable;

import com.vaadin.data.util.BeanItemContainer;

public class RepresentationContainer extends BeanItemContainer<Representation>
		implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8523151211034009406L;

	public RepresentationContainer() throws InstantiationException,
			IllegalAccessException {
		super(Representation.class);
	}

	public static RepresentationContainer createWithTestData() {
		RepresentationContainer c = null;
		try {
			c = new RepresentationContainer();
			for (int i = 0; i < 100; i++) {
				Representation p = new Representation();
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
