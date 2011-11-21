package com.opendemocracy.voting.data;

import java.io.Serializable;
import java.util.Locale;

public class Category implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2030662880000302299L;
	private long id;
	private String name;
	private String description;
	private Locale language;
	
	public Category(){
		
	}
	/**
	 * @param id
	 * @param name
	 * @param description
	 * @param language
	 */
	public Category(long id, String name, String description, Locale language) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.language = language;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
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
	 * @param language the language to set
	 */
	public void setLanguage(Locale language) {
		this.language = language;
	}
	
	
	
}
