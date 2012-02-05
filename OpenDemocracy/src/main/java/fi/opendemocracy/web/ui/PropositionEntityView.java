package fi.opendemocracy.web.ui;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.TypedQuery;

import com.invient.vaadin.charts.InvientCharts;
import com.invient.vaadin.charts.InvientCharts.PointClickEvent;
import com.invient.vaadin.charts.InvientChartsConfig;
import com.invient.vaadin.charts.InvientCharts.DecimalPoint;
import com.invient.vaadin.charts.InvientCharts.Series;
import com.invient.vaadin.charts.InvientCharts.SeriesType;
import com.invient.vaadin.charts.InvientCharts.XYSeries;
import com.invient.vaadin.charts.InvientChartsConfig.CategoryAxis;
import com.invient.vaadin.charts.InvientChartsConfig.Title;
import com.invient.vaadin.charts.InvientChartsConfig.Tooltip;
import com.invient.vaadin.charts.InvientChartsConfig.XAxis;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Slider.ValueOutOfBoundsException;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import fi.opendemocracy.domain.Category;
import fi.opendemocracy.domain.ODUser;
import fi.opendemocracy.domain.Proposition;
import fi.opendemocracy.domain.PropositionOption;
import fi.opendemocracy.domain.Vote;
import fi.opendemocracy.web.ThemeConstants;


public class PropositionEntityView extends CustomComponent implements ValueChangeListener {
	final HashMap<PropositionOption, VoteOptionSlider> votes = new HashMap<PropositionOption, VoteOptionSlider>();
	final HashMap<PropositionOption, TextField> comments = new HashMap<PropositionOption, TextField>();
	private Proposition p;
	private AbsoluteLayout mainLayout;
	private Panel scrollPanel;
	private VerticalLayout scrollContent;
	private Panel resultsPanel;
	
	public PropositionEntityView(final Proposition p){
		this.p = p;
		setCaption(p.getName());
		setIcon(ThemeConstants.TAB_ICON_CATEGORIES);
		buildMainLayout();
		setCompositionRoot(mainLayout);
	}

	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();

		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");

		// scrollPanel
		scrollPanel = buildScrollPanel();
		mainLayout.addComponent(scrollPanel);

		return mainLayout;
	}


	private Panel buildScrollPanel() {
		// common part: create layout
		scrollPanel = new Panel();
		scrollPanel.setWidth("100.0%");
		scrollPanel.setHeight("100.0%");
		scrollPanel.setImmediate(false);

		// scrollContent
		scrollContent = buildScrollContent();
		scrollPanel.setContent(scrollContent);

		return scrollPanel;
	}

	private VerticalLayout buildScrollContent() {
		VerticalLayout scrollContainer = new VerticalLayout();
		VerticalLayout scrollContent = new VerticalLayout();
		VerticalLayout resultsContent = new VerticalLayout();
		
		Label propositionName = new Label("<h1>" + p.getName() + "</h1>", Label.CONTENT_XHTML);
		Label propositionDescription = new Label("<p>" + p.getDescription() + "</p>", Label.CONTENT_XHTML);

		scrollContent.addComponent(propositionName);
		scrollContent.addComponent(propositionDescription);

		scrollContent.addComponent(new Label("Categories:", Label.CONTENT_XHTML));
		
		for (Category c : p.getCategories()) {
			scrollContent.addComponent(new Label("<p>" + c.getName() + "</p>", Label.CONTENT_XHTML));
		}

        InvientChartsConfig chartConfig = new InvientChartsConfig();
        chartConfig.getGeneralChartConfig().setType(SeriesType.COLUMN);
        chartConfig.getTitle().setText("Results");
        
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(Arrays.asList("Support","Unsure", "Dismiss"));
        LinkedHashSet<XAxis> xAxesSet = new LinkedHashSet<InvientChartsConfig.XAxis>();
        xAxesSet.add(xAxis);
        chartConfig.setXAxes(xAxesSet);
        Tooltip tooltip = new Tooltip();
        tooltip.setFormatterJsFunc("function() {"
                + " return '' + this.series.name +': '+ this.y +''; " + "}");
        chartConfig.setTooltip(tooltip);
        chartConfig.getCredit().setEnabled(false);
        InvientCharts invChart = new InvientCharts(chartConfig);

        XYSeries seriesData;
        
		for (PropositionOption o : p.getPropositionOptions()) {
			// Vote results
			
			Vote.findVotesByPropositionAndPropositionOption(p, o);
			TypedQuery<Vote> voteQuery = Vote.findVotesByPropositionAndPropositionOption(p, o);
			List<Vote> results = voteQuery.getResultList();
			BigDecimal support = new BigDecimal(0);
			BigDecimal dismiss = new BigDecimal(0);
			BigDecimal zero = new BigDecimal(0);
			int noVote = 0;
			for (Vote v : results) {
				int compare = v.getSupport().compareTo(zero);
				if (compare == 0) {
					noVote++;
				} else if (compare>0) {
					support = support.add(v.getSupport());
				} else {
					dismiss = dismiss.add(v.getSupport());
				}
			}
	        seriesData = new XYSeries(o.getName());
	        seriesData.setSeriesPoints(getPoints(seriesData, support.doubleValue(), noVote*100, dismiss.doubleValue()));
	        invChart.addSeries(seriesData);
			
	        Panel optionPanel = new Panel();
	        
			// Name and description
	        optionPanel.addComponent(new Label("<b>"+o.getName(), Label.CONTENT_XHTML));
	        optionPanel.addComponent(new Label(o.getDescription(), Label.CONTENT_XHTML));

			// Vote option slider
			VoteOptionSlider oS = new VoteOptionSlider();
			votes.put(o, oS);
			optionPanel.addComponent(oS);
			oS.addListener(this);
			
			// Vote option comment
			HorizontalLayout commentContainer = new HorizontalLayout();
			TextField comment = new TextField("Comment:");
			comments.put(o, comment);
			commentContainer.addComponent(comment);
			optionPanel.addComponent(commentContainer);
			
			scrollContent.addComponent(optionPanel);
		}
		
        invChart.addListener(new InvientCharts.PointClickListener() {

            public void pointClick(PointClickEvent pointClickEvent) {
                getApplication().getMainWindow().showNotification("PointX : " + (Double) pointClickEvent.getPoint().getX() + ", PointY : " + (Double) pointClickEvent.getPoint().getY());
            }
        });
        
		resultsPanel = new Panel();
		resultsPanel.addComponent(invChart);
		resultsPanel.setVisible(false);
		resultsContent.addComponent(resultsPanel);
		
		Button vote = new Button("Vote", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Object o = getApplication().getUser();
				if (o == null || !(o instanceof ODUser)) {
	            	getWindow().showNotification("You need to login to vote");
					return;
				}
				
				for (Entry<PropositionOption, VoteOptionSlider> e : votes.entrySet()) {
					Vote v = new Vote();
					v.setOdUser((ODUser) o);
					v.setProposition(p);
					v.setPropositionOption(e.getKey());
					v.setSupport(BigDecimal.valueOf((Double)e.getValue().getValue()));
					v.setComment((String) comments.get(e.getKey()).getValue());
					v.setTs(new Date());
					v.persist();
	            	getWindow().showNotification("Vote stored");
				}
			}
		});
		
		Button resultsButton = new Button("Show/Hide results", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				resultsPanel.setVisible(!resultsPanel.isVisible());
			}
		});
		
		HorizontalLayout footer = new HorizontalLayout();
		footer.addComponent(vote);
		footer.addComponent(resultsButton);
		scrollContent.addComponent(footer);
		scrollContent.setComponentAlignment(footer, Alignment.BOTTOM_LEFT);
		scrollContent.setMargin(true);
		scrollContent.setSpacing(false);
		scrollContent.setWidth("440px");
		scrollContainer.addComponent(scrollContent);
		scrollContainer.addComponent(resultsContent);
		return scrollContainer;
	}

    private static LinkedHashSet<DecimalPoint> getPoints(Series series,
            double... values) {
        LinkedHashSet<DecimalPoint> points = new LinkedHashSet<DecimalPoint>();
        for (double value : values) {
            points.add(new DecimalPoint(series, value));
        }
        return points;
    }
	
	@Override
	public void attach() {
		super.attach();

		List<Vote> currentVotes = null;
			
		Object user = getApplication().getUser();
		if (user != null && user instanceof ODUser) {
			TypedQuery<Vote> voteQuery = Vote.findVotesByOdUserAndProposition((ODUser) user, p);
			currentVotes = voteQuery.getResultList();
		}
		if (currentVotes != null) {
			for (PropositionOption o : votes.keySet()) {
				for (Vote v : currentVotes) {
					if (v.getPropositionOption().getId() == o.getId()) {
						try {
							votes.get(o).setValue(v.getSupport().doubleValue());
							comments.get(o).setValue(v.getComment());
						} catch (ValueOutOfBoundsException e) {
							// TODO Use the experts trusted by the user as representatives
							e.printStackTrace();
						}
						break;
					}
				}
			}
		}
	}

	public void valueChange(ValueChangeEvent event) {
		Property property = event.getProperty();
		VoteOptionSlider oS = (VoteOptionSlider) property;
		oS.setMaxAllowed((Double)oS.getValue());
		// TODO Auto-generated method stub

	}
}
