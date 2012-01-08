package com.opendemocracy.voting;

import com.opendemocracy.voting.ui.CategoryAddExpertForm;
import com.opendemocracy.voting.ui.CategoryList;
import com.opendemocracy.voting.ui.CategoryView;
import com.opendemocracy.voting.ui.ExpertList;
import com.opendemocracy.voting.ui.ExpertView;
import com.opendemocracy.voting.ui.TrustExpertModalView;
import com.opendemocracy.voting.ui.MainView;
import com.opendemocracy.voting.ui.PropositionAddForm;
import com.opendemocracy.voting.ui.PropositionForm;
import com.opendemocracy.voting.ui.PropositionList;
import com.opendemocracy.voting.ui.PropositionMainView;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalSplitPanel;

public class ViewManager {
	private PropositionList propositionList;
	private ExpertList expertList;
	private PropositionAddForm propositionForm;
	private PropositionMainView propositionMainView;
	private CategoryAddExpertForm categoryAddExpertForm;
	private CategoryView categoryView;
	private CategoryList categoryList;
	private ExpertView expertView;
	private VerticalSplitPanel layout;
	private VotingApplication app;
	private MainView mainView;

	public ViewManager(VotingApplication app) {
		this.app = app;
	}

//	public void valueChange(ValueChangeEvent event) {
//		Property property = event.getProperty();
//		if (property == propositionList) {
//			Item item = propositionList.getItem(propositionList.getValue());
//			showPropositionTab(item);
//			if (item != getPropositionForm().getItemDataSource()) {
//				getPropositionForm().setItemDataSource(item);
//			}
//		}
//	}

	public void showPropositionTab(Item item) {
		getLayout().setSecondComponent(getPropositionMainView());
		getPropositionMainView().showAddProposition();
	}

	public void showListView() {
		getLayout().setSecondComponent(getPropositionMainView());
		getPropositionMainView().showPropositionList();
	}

//	public void showExperts() {
//		getLayout().setSecondComponent(new Label("Experts"));
//	}

	public void buttonClick(ClickEvent event) {
		mainView.buttonClick(event);
	}

	public PropositionAddForm getPropositionForm() {
		if (propositionForm == null) {
			propositionForm = new PropositionAddForm(app);
		}
		return propositionForm;
	}

	public PropositionList getPropositionList() {
		if (propositionList == null) {
			propositionList = new PropositionList(app);
		}
		return propositionList;
	}
	
	public PropositionMainView getPropositionMainView() {
		if (propositionMainView == null) {
			propositionMainView = new PropositionMainView(app);
		}
		return propositionMainView;
	}

	private MainView getMainView() {
		if (mainView == null) {
			mainView = new MainView(app);
		}
		return mainView;
	}

	public VerticalSplitPanel getLayout() {
		if (layout == null) {
			layout = new VerticalSplitPanel();
			layout.setLocked(true);
			layout.setFirstComponent(getMainView().createToolbar());
			layout.setSecondComponent(getPropositionMainView());
			layout.setSplitPosition(
					(int) Math.ceil(layout.getFirstComponent().getHeight()),
					layout.getFirstComponent().getHeightUnits());
		}
		return layout;
	}

	public void showCategories() {
		getLayout().setSecondComponent(getCategoryView());
		getCategoryView().showCategoryList();
	}

	public void showExperts() {
		getLayout().setSecondComponent(getExpertView());
		getExpertView().showExpertList();
	}
	
	public CategoryView getCategoryView() {
		if (categoryView == null) {
			categoryView = new CategoryView(app);
		}
		return categoryView;
	}
	
	private ExpertView getExpertView() {
	if (expertView == null) {
		expertView = new ExpertView(app);
	}
	return expertView;
}

	public CategoryAddExpertForm getCategoryAddExpertForm() {
		if (categoryAddExpertForm == null) {
			categoryAddExpertForm = new CategoryAddExpertForm(app);
		}
		return categoryAddExpertForm;
	}

	public CategoryList getCategoryList() {
		if (categoryList == null) {
			categoryList = new CategoryList(app);
		}
		return categoryList;
	}

	public ExpertList getExpertList() {
		if (expertList == null) {
			expertList = new ExpertList(app);
		}
		return expertList;
	}

}