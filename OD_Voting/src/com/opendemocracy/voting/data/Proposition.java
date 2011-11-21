package com.opendemocracy.voting.data;

import java.io.Serializable;
import java.util.Collection;

public class Proposition implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6573890059881593472L;
	private long id;
	private User owner;
	private String targetUsers;
	private Collection<Category> targetCategories;
	/**
	 * @return
	 * @see java.util.Collection#size()
	 */
	public int size() {
		return targetCategories.size();
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	public boolean add(Category o) {
		return targetCategories.add(o);
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	public boolean remove(Object o) {
		return targetCategories.remove(o);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends Category> c) {
		return targetCategories.addAll(c);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> c) {
		return targetCategories.removeAll(c);
	}

	public Proposition(){
		
	}

	/**
	 * @param id
	 * @param owner
	 * @param targetUsers
	 */
	public Proposition(long id, User owner, String targetUsers) {
		super();
		this.id = id;
		this.owner = owner;
		this.targetUsers = targetUsers;
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
	 * @return the owner
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

	/**
	 * @return the targetUsers
	 */
	public String getTargetUsers() {
		return targetUsers;
	}

	/**
	 * @param targetUsers the targetUsers to set
	 */
	public void setTargetUsers(String targetUsers) {
		this.targetUsers = targetUsers;
	}

	/**
	 * @return the targetCategories
	 */
	public Category[] getTargetCategories() {
		return (Category[])targetCategories.toArray();
	}
}
