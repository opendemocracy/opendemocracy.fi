package com.opendemocracy.voting.data;

import java.io.Serializable;
import java.util.Locale;

public class Option implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6153580024525090167L;
	private long id;
	private String title;
	private String description;
	private Locale language;
	private Proposition proposition;

	public Option() {

	}

	/**
	 * @param id
	 * @param description
	 * @param language
	 */
	public Option(long id, String title, String description, Locale language) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.language = language;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the language
	 */
	public Locale getLanguage() {
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(Locale language) {
		this.language = language;
	}

	/**
	 * @return the proposition
	 */
	public Proposition getProposition() {
		return proposition;
	}

	/**
	 * @param proposition
	 *            the proposition to set
	 */
	public void setProposition(Proposition proposition) {
		this.proposition = proposition;
	}

	@Override
	public String toString() {
		return description;
	}

}
