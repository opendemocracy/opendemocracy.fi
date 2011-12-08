package com.opendemocracy.voting.data;

import java.io.Serializable;
import java.util.ArrayList;

import com.vaadin.data.util.BeanItemContainer;

public class PropositionContainer extends BeanItemContainer<Proposition>
		implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8523151211034009406L;
	/**
	 * Natural property order for Person bean. Used in tables and forms.
	 */
	public final static Object[] NATURAL_COL_ORDER = new Object[] { "id",
			"owner", "targetUsers", "description" };

	/**
	 * "Human readable" captions for properties in same order as in
	 * NATURAL_COL_ORDER.
	 */
	public final static String[] COL_HEADERS_ENGLISH = new String[] { "Id",
			"Owner", "Targeted towards", "Description" };

	public PropositionContainer() throws InstantiationException,
			IllegalAccessException {
		super(Proposition.class);
	}

	public static PropositionContainer createWithTestData() {
		PropositionContainer c = null;
		ArrayList<Option> testOptions = new ArrayList<Option>();
		testOptions.add(new Option(0, "Option 1", "Option 1 Description", null));
		testOptions.add(new Option(0, "Option 2", "Option 2 Description", null));
		testOptions.add(new Option(0, "Option 3", "Option 3 Description", null));
		try {
			c = new PropositionContainer();
			for (int i = 0; i < 100; i++) {
				Proposition p = new Proposition(i, new User(i, null, null),
						"User #" + i, new ArrayList<Category>(),
						testOptions, "Title #" + i, "Description #" + i);
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
