package com.opendemocracy.voting.data;

import java.io.Serializable;

public class Vote implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5530723852072496536L;
	private long id;
	//User voting
	private User user;
	//Option selected
	private Option option;
	//Amount of support for option from user
	private float support;
	
	public Vote(){
		
	}
	/**
	 * @param id
	 * @param user
	 * @param option
	 * @param support
	 */
	public Vote(long id, User user, Option option, float support) {
		super();
		this.id = id;
		this.user = user;
		this.option = option;
		this.support = support;
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
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the option
	 */
	public Option getOption() {
		return option;
	}
	/**
	 * @param option the option to set
	 */
	public void setOption(Option option) {
		this.option = option;
	}
	/**
	 * @return the support
	 */
	public float getSupport() {
		return support;
	}
	/**
	 * @param support the support to set
	 */
	public void setSupport(float support) {
		this.support = support;
	}

	
}
