package com.opendemocracy.voting.data;

import java.io.Serializable;

public class Representation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8688118385994229854L;
	private User user;
	private Category category;
	private Expert expert;
	private float trust;

	public Representation() {

	}

	/**
	 * @param user
	 * @param category
	 * @param expert
	 * @param trust
	 */
	public Representation(User user, Category category, Expert expert,
			float trust) {
		super();
		this.user = user;
		this.category = category;
		this.expert = expert;
		this.trust = trust;
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
	 * @return the expert
	 */
	public Expert getExpert() {
		return expert;
	}

	/**
	 * @param expert
	 *            the expert to set
	 */
	public void setExpert(Expert expert) {
		this.expert = expert;
	}

	/**
	 * @return the trust
	 */
	public float getTrust() {
		return trust;
	}

	/**
	 * @param trust
	 *            the trust to set
	 */
	public void setTrust(float trust) {
		this.trust = trust;
	}

}
