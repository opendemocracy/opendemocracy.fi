package com.opendemocracy.voting.ui;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.opendemocracy.voting.VotingApplication;
import com.opendemocracy.voting.data.Proposition;
import com.opendemocracy.voting.data.PropositionContainer;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
public class PropositionForm extends Form implements ClickListener {

	private Button save = new Button("Save", (ClickListener) this);
	private Button cancel = new Button("Cancel", (ClickListener) this);
	private Button edit = new Button("Edit", (ClickListener) this);
	private final ComboBox targetGroups = new ComboBox("Target group");

	private VotingApplication app;
	private boolean newPropositionMode = false;
	private Proposition newProposition = null;

	public PropositionForm(VotingApplication app) {
		this.app = app;

		/*
		 * Enable buffering so that commit() must be called for the form before
		 * input is written to the data. (Form input is not written immediately
		 * through to the underlying object.)
		 */
		setWriteThrough(false);

		HorizontalLayout footer = new HorizontalLayout();
		footer.setSpacing(true);
		footer.addComponent(save);
		footer.addComponent(cancel);
		footer.addComponent(edit);
		footer.setVisible(false);

		setFooter(footer);

		/* Allow the user to enter new target group */
		targetGroups.setNewItemsAllowed(true);
		/* We do not want to use null values */
		targetGroups.setNullSelectionAllowed(false);
		/* Add an empty group used for selecting no group */
		targetGroups.addItem("");

		/* Populate combobox from datasource */
		PropositionContainer ds = app.getDataSource();
		for (Iterator<Proposition> it = ds.getItemIds().iterator(); it.hasNext();) {
			String city = (it.next()).getTargetUsers();
			targetGroups.addItem(city);
		}

		/*
		 * Field factory for overriding how the component for city selection is
		 * created
		 */
		setFormFieldFactory(new DefaultFieldFactory() {
			@Override
			public Field createField(Item item, Object propertyId,
					Component uiContext) {
				if (propertyId.equals("targetUsers")) {
					targetGroups.setWidth("200px");
					return targetGroups;
				}

				Field field = super.createField(item, propertyId, uiContext);
				if (propertyId.equals("id")) {
					TextField tf = (TextField) field;
					/*
					 * We do not want to display "null" to the user when the
					 * field is empty
					 */
					tf.setNullRepresentation("");
					tf.setRequired(true);
				} else if (propertyId.equals("owner")) {
					field.setRequired(true);
				}

				field.setWidth("200px");
				return field;
			}
		});
	}

	public void buttonClick(ClickEvent event) {
		Button source = event.getButton();

		if (source == save) {
			/* If the given input is not valid there is no point in continuing */
			if (!isValid()) {
				return;
			}

			commit();
			if (newPropositionMode) {
				/* We need to add the new person to the container */
				Item addedItem = app.getDataSource().addItem(newProposition);
				/*
				 * We must update the form to use the Item from our datasource
				 * as we are now in edit mode (no longer in add mode)
				 */
				setItemDataSource(addedItem);

				newPropositionMode = false;
			}
			setReadOnly(true);
		} else if (source == cancel) {
			if (newPropositionMode) {
				newPropositionMode = false;
				/* Clear the form and make it invisible */
				setItemDataSource(null);
			} else {
				discard();
			}
			setReadOnly(true);
		} else if (source == edit) {
			setReadOnly(false);
		}
	}

	@Override
	public void setItemDataSource(Item newDataSource) {
			newPropositionMode = false;
		if (newDataSource != null) {
			List<Object> orderedProperties = Arrays
					.asList(PropositionContainer.NATURAL_COL_ORDER);
			super.setItemDataSource(newDataSource, orderedProperties);

			setReadOnly(true);
			getFooter().setVisible(true);
		} else {
			super.setItemDataSource(null);
			getFooter().setVisible(false);
		}
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
		save.setVisible(!readOnly);
		cancel.setVisible(!readOnly);
		edit.setVisible(readOnly);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addProposition() {
		// Create a temporary item for the form
		newProposition = new Proposition();
		setItemDataSource(new BeanItem(newProposition));
                newPropositionMode = true;
		setReadOnly(false);
	}

}