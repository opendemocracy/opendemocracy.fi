package com.opendemocracy.voting.data;
import java.io.Serializable;

import com.vaadin.data.util.BeanItemContainer;

public class PropositionContainer extends BeanItemContainer<Proposition> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8523151211034009406L;
	/**
	 * Natural property order for Person bean. Used in tables and forms.
	 */
	public final static Object[] NATURAL_COL_ORDER = new Object[] {
			"id", "targetUsers"};

	/**
	 * "Human readable" captions for properties in same order as in
	 * NATURAL_COL_ORDER.
	 */
	public final static String[] COL_HEADERS_ENGLISH = new String[] {
			"Proposition id", "Targeted towards"};
	
	public PropositionContainer() throws InstantiationException, IllegalAccessException {
		super(Proposition.class);
	}

	public static PropositionContainer createWithTestData() {
		PropositionContainer c = null;
		try {
			c = new PropositionContainer();
			for (int i = 0; i < 100; i++) {
				Proposition p = new Proposition(i, new User(i, null, null), "Proposition #"+i);
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
