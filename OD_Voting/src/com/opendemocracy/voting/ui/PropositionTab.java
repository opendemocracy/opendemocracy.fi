package com.opendemocracy.voting.ui;

import java.util.ArrayList;
import java.util.Iterator;

import com.opendemocracy.voting.VotingApplication;
import com.opendemocracy.voting.components.VoteOptionSlider;
import com.opendemocracy.voting.data.Category;
import com.opendemocracy.voting.data.Option;
import com.opendemocracy.voting.data.Proposition;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.AbstractSelect.NewItemHandler;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
public class PropositionTab extends VerticalLayout implements
Property.ValueChangeListener, NewItemHandler {
	
	private ComboBox tagBox;
	private VotingApplication app;
	private VerticalLayout categoryList;
	public PropositionTab(final Proposition p, final VotingApplication vApp){
		app = vApp;
		//Properties
		setWidth("100%");
		setMargin(true);
		setSpacing(false);
		
		//Description
		Label pDesc = new Label(p.getDescription());
		pDesc.setContentMode(Label.CONTENT_XHTML);
		pDesc.setWidth("100%");
		addComponent(pDesc);
		
		//Options + slider
		ArrayList<Option> pOptions = new ArrayList<Option>(p.getOptions());
		for (Iterator<Option> it = (Iterator<Option>) pOptions.iterator(); it
				.hasNext();) {
			Option o = it.next();
			Label title = new Label("<b>"+o.getTitle()+"</b>");
			title.setContentMode(Label.CONTENT_XHTML);
			addComponent(title);
			VoteOptionSlider oS = new VoteOptionSlider();
			oS.setWidth("50%");
			addComponent(oS);
			addComponent(new Label(o.getDescription()));
		}
		
		//Categories
		ArrayList<Category> pCategories = new ArrayList<Category>(p.getTargetCategories());
		categoryList = new VerticalLayout();
		for (Iterator<Category> it = (Iterator<Category>) pCategories.iterator(); it
				.hasNext();) {
			Category c = it.next();
			Label title = new Label(c.getName());
			title.setDescription(c.getDescription());
			categoryList.addComponent(title);
		}
		addComponent(categoryList);
		

		HorizontalLayout assignCategories = new HorizontalLayout();
		tagBox = new ComboBox("Assign category to proposition");
        pCategories = new ArrayList<Category>(vApp.getCategoryData().getItemIds());
        for (Iterator<Category> it = (Iterator<Category>) pCategories.iterator(); it
				.hasNext();) {
        	tagBox.addItem(it.next());
        }
        tagBox.setNewItemsAllowed(true);
        tagBox.setNewItemHandler(this);
        tagBox.setImmediate(true);
        tagBox.addListener(this);
        
        Button bAssign = new Button("Assign", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				categoryList.addComponent(new Label(tagBox.getValue().toString()));
				p.addCategory((Category)tagBox.getValue());
				tagBox.setValue(null);			}
		});
        assignCategories.addComponent(tagBox);
        assignCategories.addComponent(bAssign);
        addComponent(assignCategories);
		/*
		vApp.addCategory(c);
		p.addCategory(o);
		*/
		
		//Footer
		Button vote = new Button("Vote", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				app.getMainWindow().showNotification("TODO: Register vote");
			}
		});
		Button cancel = new Button("Clear", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				app.getMainWindow().showNotification("TODO: Clear");
			}
		});
		HorizontalLayout footer = new HorizontalLayout();
		footer.addComponent(vote);
		footer.addComponent(cancel);
		addComponent(footer);
	}

	public void valueChange(ValueChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

    public void addNewItem(String newItemCaption) {
        if (!tagBox.containsId(newItemCaption)) {
            getWindow().showNotification("Created category: " + newItemCaption);
            Category newCat = new Category();
            newCat.setName(newItemCaption);
            newCat.setDescription(newItemCaption);
            //TODO: Create category modal, description
            app.addCategory(newCat);
            tagBox.addItem(newCat);
            tagBox.setValue(newItemCaption);
        }
    }
}
