package com.opendemocracy.voting.data;

import java.io.Serializable;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3679178688736091808L;
	private long id;
	private Object session, proof;

	public User() {

	}

	/**
	 * @param id
	 * @param session
	 * @param proof
	 */
	public User(long id, Object session, Object proof) {
		super();
		this.id = id;
		this.session = session;
		this.proof = proof;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the session
	 */
	public Object getSession() {
		return session;
	}

	/**
	 * @param session
	 *            the session to set
	 */
	public void setSession(Object session) {
		this.session = session;
	}

	/**
	 * @return the proof
	 */
	public Object getProof() {
		return proof;
	}

	/**
	 * @param proof
	 *            the proof to set
	 */
	public void setProof(Object proof) {
		this.proof = proof;
	}

}
