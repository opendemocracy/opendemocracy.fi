package com.opendemocracy.voting.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

public class Proposition implements Serializable {
	private static final long serialVersionUID = -6573890059881593472L;
	private long id;
	private User owner;
	private String targetUsers;
	private Collection<Category> targetCategories;
	private Collection<Option> options;
	private String description;

	public Proposition() {

	}

	public Proposition(long id, User owner, String targetUsers,
			Collection<Category> targetCategories, Collection<Option> options,
			String description) {
		super();
		this.id = id;
		this.owner = owner;
		this.targetUsers = targetUsers;
		this.targetCategories = targetCategories;
		this.options = options;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTargetUsers() {
		return targetUsers;
	}

	public void setTargetUsers(String targetUsers) {
		this.targetUsers = targetUsers;
	}

	public Collection<Option> getOptions() {
		return Collections.unmodifiableCollection(options);
	}

	public Collection<Category> getTargetCategories() {
		return Collections.unmodifiableCollection(targetCategories);
	}

	public int nOptions() {
		return options.size();
	}

	public int nCategories() {
		return targetCategories.size();
	}

	public boolean addOption(Option o) {
		return options.add(o);
	}

	public boolean addCategory(Category o) {
		return targetCategories.add(o);
	}

	public boolean removeOption(Option o) {
		return options.remove(o);
	}

	public boolean removeCategory(Category o) {
		return targetCategories.remove(o);
	}
}
