package com.opendemocracy.voting.data;

import java.io.Serializable;

public class Expert implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4920773264075330416L;
	private long id;

	private Category category;
	private User user;

	public Expert() {

	}

	/**
	 * @param category
	 * @param user
	 */
	public Expert(long id, Category category, User user) {
		super();
		this.id = id;
		this.category = category;
		this.user = user;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

}
