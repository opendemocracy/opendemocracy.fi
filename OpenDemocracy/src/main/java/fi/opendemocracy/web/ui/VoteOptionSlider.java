package fi.opendemocracy.web.ui;

import com.vaadin.ui.Slider;

public class VoteOptionSlider extends Slider{
	//TODO: Colors, setMaxAllowed (Disable part > maxallowed) 

	public VoteOptionSlider(){
		super();
		super.setMax(100);
		super.setMin(-100);
		super.setWidth("100%");
		super.addStyleName("option_slider");
        super.setImmediate(true);
        
        //TODO Paint slider red on negative side, green on positive, grey right of maxAllowed
	}

	public VoteOptionSlider(String caption){
		this();
		setCaption(caption);
	}
	
	//TODO Lock slider to max value max
	public void setMaxAllowed(double max) {

	}
}