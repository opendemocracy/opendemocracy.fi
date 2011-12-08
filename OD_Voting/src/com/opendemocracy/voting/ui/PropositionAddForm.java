package com.opendemocracy.voting.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.opendemocracy.voting.VotingApplication;
import com.opendemocracy.voting.data.Option;
import com.opendemocracy.voting.data.Proposition;
import com.vaadin.data.Item;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
/*Add proposition form, will replace PropositionForm when finished*/
public class PropositionAddForm extends Form {
	private static final long serialVersionUID = 1L;
	
	//Data containers
	private VotingApplication app;
	private Proposition newProposition = null;
	private ArrayList<Option> pOptions = null;
	
	//Proposition components
	private final TextField pTitle = new TextField();
	private final RichTextArea pDescription = new RichTextArea();
	private final Button pPreview = new Button("Preview");
	private final Button pDiscard = new Button("Discard");
	
	//Options editor components
	private int oCurrentOption = 0;
	private AbsoluteLayout oEditor = null;
	private TextArea oDescription = new TextArea();
	private ListSelect oSelect = null;
	private Button oAdd = new Button("Add");
	private TextField oTitle = new TextField();
	
	//Construct
	public PropositionAddForm(VotingApplication app){
		//General settings
		this.app = app;
		setWriteThrough(false);
		getLayout().addComponent(new Label("Add proposition"));
		
		//Form footer
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSpacing(true);
		footer.addComponent(pPreview);
		footer.addComponent(pDiscard);
		setFooter(footer);
		//pPreview
		pPreview.addListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				preview();
			}
		});
				
		//pDiscard
		pDiscard.addListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				getWindow().showNotification("TODO:Discard");
			}
		});
		
		//pTitle
		pTitle.setInputPrompt("Title");
		pTitle.setNullSettingAllowed(true);
		pTitle.setWidth("440px");
		pTitle.setNullSettingAllowed(false);
		
		//pDescription
		pDescription.setNullSettingAllowed(false);
		pDescription.setWidth("440px");

		//Build form
		getLayout().addComponent(pTitle);
		getLayout().addComponent(pDescription);
		getLayout().addComponent(new Label("Manage options"));
		getLayout().addComponent(getOptionsEditor());
		setFooter(footer);
	}

	private AbsoluteLayout getOptionsEditor(){
		if(oEditor == null){
			// create layout
			oEditor = new AbsoluteLayout();
			oEditor.setImmediate(false);
			oEditor.setWidth("440px");
			oEditor.setHeight("260px");
			oEditor.setMargin(false);
			
			// oTitle
			oTitle = new TextField();
			oTitle.setImmediate(false);
			oTitle.setWidth("135px");
			oTitle.setHeight("25px");
			oTitle.setInputPrompt("Title");
			oEditor.addComponent(oTitle, "top:0.0px;left:0.0px;");
			
			// oDescription
			oDescription = new TextArea();
			oDescription.setImmediate(false);
			oDescription.setWidth("260px");
			oDescription.setHeight("180px");
			oDescription.setInputPrompt("Option description");
			oDescription.addListener(new BlurListener(){
				public void blur(BlurEvent event) {
					Option oNew = (Option) oSelect.getValue();
					oNew.setDescription(oDescription.getValue().toString());
					oSelect.setValue(oNew);
					getWindow().showNotification("Description updated");
				}
			});
			oEditor.addComponent(oDescription, "top:0.0px;left:180.0px;");

			// oSelect
			oSelect = new ListSelect();
			oSelect.setImmediate(true);
			oSelect.setNewItemsAllowed(true);
			oSelect.setWidth("180px");
			oSelect.setHeight("155px");
			oSelect.setNullSelectionAllowed(false);
			oSelect.addListener(new ValueChangeListener() {
				public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
					Option newSelection = (Option)oSelect.getValue();
					oDescription.setValue(newSelection.getDescription());
				}
			});
			oEditor.addComponent(oSelect, "top:26.0px;left:0.0px;");
			
			
			// oAdd
			oAdd = new Button("Add");
			oAdd.setImmediate(true);
			oAdd.setWidth("45px");
			oAdd.setHeight("25px");
			oAdd.addListener(new ClickListener() {
				public void buttonClick(ClickEvent event) {
					Option addMe = new Option(0,oTitle.getValue().toString(),"",null);
					oSelect.addItem(addMe);
					oSelect.select(addMe);
				}
			});
			oEditor.addComponent(oAdd, "top:0.0px;left:135.0px;");
			
		}
		return oEditor;
			
		}

		private void preview(){
			pOptions = new ArrayList(oSelect.getRows());
			for (Iterator<Option> it = (Iterator<Option>) oSelect.getItemIds().iterator(); it
					.hasNext();) {
				pOptions.add(it.next());
			}

			newProposition = new Proposition(0L, new com.opendemocracy.voting.data.User(), "targetUsers",
					null, pOptions, pTitle.toString(),pDescription.toString());
			app.getMainWindow()
			.addWindow(
					new ModalWindow(app, newProposition)
					);
		}
	
}
