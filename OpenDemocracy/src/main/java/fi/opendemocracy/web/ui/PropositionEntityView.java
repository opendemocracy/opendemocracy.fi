package fi.opendemocracy.web.ui;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

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
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Slider.ValueOutOfBoundsException;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import fi.opendemocracy.domain.Category;
import fi.opendemocracy.domain.Expert;
import fi.opendemocracy.domain.ODUser;
import fi.opendemocracy.domain.Proposition;
import fi.opendemocracy.domain.PropositionOption;
import fi.opendemocracy.domain.Representation;
import fi.opendemocracy.domain.Vote;
import fi.opendemocracy.web.ThemeConstants;


public class PropositionEntityView extends CustomComponent implements ValueChangeListener {
	final HashMap<PropositionOption, VoteOptionSlider> votes = new HashMap<PropositionOption, VoteOptionSlider>();
	final HashMap<PropositionOption, TextField> comments = new HashMap<PropositionOption, TextField>();
	private Proposition p;
	private AbsoluteLayout mainLayout;
	private Panel scrollPanel;
	private VerticalLayout scrollContent;
	
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
		scrollPanel.setStyle(Panel.STYLE_LIGHT);
		return scrollPanel;
	}

	private VerticalLayout buildScrollContent() {
		VerticalLayout scrollContainer = new VerticalLayout();
		VerticalLayout scrollContent = new VerticalLayout();
		
		Label propositionName = new Label("<h1>" + p.getName() + "</h1>", Label.CONTENT_XHTML);
		Label propositionDescription = new Label("<p>" + p.getDescription() + "</p>", Label.CONTENT_XHTML);

		scrollContent.addComponent(propositionName);
		scrollContent.addComponent(propositionDescription);

		scrollContent.addComponent(new Label("Categories:", Label.CONTENT_XHTML));
		
		for (Category c : p.getCategories()) {
			scrollContent.addComponent(new Label("<p>" + c.getName() + "</p>", Label.CONTENT_XHTML));
		}
        
		for (PropositionOption o : p.getPropositionOptions()) {
	        Panel optionPanel = new Panel();
	        
			// Name and description
	        optionPanel.addComponent(new Label("<b>"+o.getName(), Label.CONTENT_XHTML));
	        optionPanel.addComponent(new Label(o.getDescription(), Label.CONTENT_XHTML));

			// Vote option slider
			VoteOptionSlider oS = new VoteOptionSlider();
			votes.put(o, oS);
			optionPanel.addComponent(oS);
			optionPanel.setWidth("440px");
			oS.addListener(this);
			
			// Vote option comment
			
			TextField comment = new TextField();
			comment.addListener(new FocusListener() {
				
				@Override
				public void focus(FocusEvent event) {
					if (((TextField)event.getSource()).getValue() == null) {
						((TextField)event.getSource()).setValue("");
					}
				}
			});
			comment.addListener(new BlurListener() {
				
				@Override
				public void blur(BlurEvent event) {
					if (((TextField)event.getSource()).getValue() == "") {
						((TextField)event.getSource()).setValue(null);
					}
				}
			});
			comment.setNullRepresentation("Comment");
			comment.setValue(null);
			comment.setWidth("100%");
			comments.put(o, comment);
			optionPanel.addComponent(comment);
			scrollContent.addComponent(optionPanel);
		}

        final InvientCharts invChart = createChart("Results", Arrays.asList("Support","Unsure", "Dismiss","Sum"));
        final InvientCharts invChart2 = createChart("Suggested results", Arrays.asList("Sum"));
        
        final PopupDateField maxdate = new PopupDateField();
        maxdate.setValue(new java.util.Date());
        maxdate.setResolution(PopupDateField.RESOLUTION_DAY);
        maxdate.addListener(new ValueChangeListener(){
		    public void valueChange(ValueChangeEvent event) {
		        Object value = event.getProperty().getValue();
		        if (value == null || !(value instanceof Date)) {
		            getWindow().showNotification("Invalid date entered");
		        } else {
					invChart.setSeries(getResults((Date) maxdate.getValue()));
					invChart2.setSeries(getSuggestedWeigths((Date) maxdate.getValue()));
		        }
		    }
        });
        maxdate.setImmediate(true);
		invChart.setSeries(getResults((Date) maxdate.getValue()));
		invChart2.setSeries(getSuggestedWeigths((Date) maxdate.getValue()));
		
		final VerticalLayout resultsContent = new VerticalLayout();
        HorizontalLayout timelimit = new HorizontalLayout();
        timelimit.addComponent(new Label("Choose upper timelimit for vote results:&nbsp;&nbsp;", Label.CONTENT_XHTML));
        timelimit.addComponent(maxdate);
		resultsContent.addComponent(timelimit);
		resultsContent.addComponent(invChart);
		resultsContent.addComponent(invChart2);
		resultsContent.setMargin(true);
		resultsContent.setSpacing(false);
		resultsContent.setVisible(false);
		
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
				}
            	getWindow().showNotification("Vote stored");
            	maxdate.setValue(new Date());
				invChart.setSeries(getResults((Date) maxdate.getValue()));
			}
		});
		
		Button resultsButton = new Button("Show/Hide results", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				resultsContent.setVisible(!resultsContent.isVisible());
			}
		});
		
		HorizontalLayout footer = new HorizontalLayout();
		footer.addComponent(vote);
		footer.setMargin(true);
		footer.setSpacing(true);
		footer.addComponent(resultsButton);
		scrollContent.addComponent(footer);
		scrollContent.setMargin(true);
		scrollContent.setSpacing(false);
		scrollContainer.addComponent(scrollContent);
		scrollContainer.addComponent(resultsContent);
		return scrollContainer;
	}

	private InvientCharts createChart(String string, List<String> categories) {
        InvientChartsConfig chartConfig = new InvientChartsConfig();
        chartConfig.getGeneralChartConfig().setType(SeriesType.COLUMN);
        chartConfig.getTitle().setText(string);
        
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(categories);
        LinkedHashSet<XAxis> xAxesSet = new LinkedHashSet<InvientChartsConfig.XAxis>();
        xAxesSet.add(xAxis);
        chartConfig.setXAxes(xAxesSet);
        Tooltip tooltip = new Tooltip();
        tooltip.setFormatterJsFunc("function() {"
                + " return '' + this.series.name +': '+ this.y +''; " + "}");
        chartConfig.setTooltip(tooltip);
        chartConfig.getCredit().setEnabled(false);
        InvientCharts invChart = new InvientCharts(chartConfig);
        invChart.setWidth("-1px");
        invChart.addListener(new InvientCharts.PointClickListener() {

            public void pointClick(PointClickEvent pointClickEvent) {
                getApplication().getMainWindow().showNotification("PointX : " + (Double) pointClickEvent.getPoint().getX() + ", PointY : " + (Double) pointClickEvent.getPoint().getY());
            }
        });
		return invChart;
	}

	private LinkedHashSet<Series> getResults(Date timelimit) {
		LinkedHashSet<Series> list = new LinkedHashSet<InvientCharts.Series>();
        
		for (PropositionOption o : p.getPropositionOptions()) {
			List<Vote> results = Vote.findVotesByPropositionAndPropositionOptionLatestBefore(p, o, timelimit).getResultList();
			
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
			BigDecimal sum = new BigDecimal(0).add(support).add(dismiss);

	        XYSeries seriesData = new XYSeries(o.getName());
	        seriesData.setSeriesPoints(getPoints(seriesData, support.doubleValue(), noVote*100, dismiss.doubleValue(), sum.doubleValue()));
	        list.add(seriesData);
		}
		return list;
	}

	/** Calculate suggested vote using current users trusted experts
	 */
	private LinkedHashSet<Series> getSuggestedWeigths(Date timeline) {
		LinkedHashSet<Series> list = new LinkedHashSet<InvientCharts.Series>();
        
    	HashMap<PropositionOption, BigDecimal> map = new HashMap<PropositionOption, BigDecimal>(p.getPropositionOptions().size());

    	for (PropositionOption po : p.getPropositionOptions()) {
    		map.put(po, BigDecimal.ZERO);
    	}
    	
    	Set<ODUser> voters = new HashSet<ODUser>();
		List<Vote> results = Vote.findVotesByPropositionLatestBefore(p, timeline).getResultList();
    	for (Vote v : results) {
    		voters.add(v.getOdUser());
    	}
    	List<ODUser> nonVoters = ODUser.findAllODUsers();
    	nonVoters.removeAll(voters);
    	for (ODUser nonVoter : nonVoters) {
    		List<Representation> representations = Representation.findRepresentationsByOdUserAndTrustGreaterThanLatestBefore(nonVoter, BigDecimal.ZERO, timeline).getResultList();
    		for (Representation r : representations) {
        		if (voters.contains(r.getExpert()) && p.getCategories().contains(r.getExpert().getCategory())) {
        			for (Vote v : results) {
			    		if (v.getOdUser().getId().equals(r.getExpert().getOdUser().getId())){
			    			BigDecimal support = map.get(v.getPropositionOption()).add(v.getSupport().multiply(r.getTrust()));
			    			map.put(v.getPropositionOption(), support);
			    		}
			    	}
        		}
        	}
    	}
		for (PropositionOption o : map.keySet()) {
	        XYSeries seriesData = new XYSeries(o.getName());
	        seriesData.setSeriesPoints(getPoints(seriesData, map.get(o).doubleValue()));
	        list.add(seriesData);
		}
		return list;
	}

	/** Calculate vote power in a proposition
	 */
    private HashMap<Vote, Double> getVoteWeigths(List<Vote> results) {
    	HashMap<Vote, Double> map = new HashMap<Vote, Double>(results.size());
    	Set<ODUser> voters = new HashSet<ODUser>();
    	for (Vote v : results) {
    		voters.add(v.getOdUser());
    	}
    	for (Vote v : results) {
    		map.put(v, getVoteWeigth(v.getOdUser(), voters));
    	}
		return map;
	}

	private Double getVoteWeigth(ODUser odUser, Set<ODUser> voters) {
		if (voters.contains(odUser)) {
			// If you vote yourself, you do not delegate your voting power
			return new Double(0);
		}
		List<Expert> experts = Expert.findExpertsByOdUser(odUser).getResultList();
		if (experts.size() == 0) {
			// If not an expert use unit weight, as no one has given your vote/trust any delegation power
			return new Double(1);
		}
		Double w = new Double(0); // Sum the given trust
		for (Category c : p.getCategories()) {
			// Calculate for each category the proposition has and the voter claims to be an expert in
			for (Expert e : experts) {
    			if (e.getCategory().getId().equals(c.getId())) {
    				List<Representation> representations = Representation.findRepresentationsByExpertAndTrustGreaterThan(e, BigDecimal.ZERO).getResultList();
    				// Add each non-voting user that trusts the voter up to n=2 levels
    				int n = 2;
    				for (Representation r : representations) {
    					w += getVoteWeigth(r.getOdUser(), voters, c, n, e);
    				}
    			}
			}
		}
		return w;
	}

	private Double getVoteWeigth(ODUser odUser, Set<ODUser> voters, Category c, int n, Expert expert) {
		// Add each non-voting user that has a chain of trust
		if (voters.contains(odUser) || n < 1) {
			return new Double(0);
		}
		List<Expert> experts = Expert.findExpertsByOdUser(odUser).getResultList();
		if (experts.size() == 0) {
			// If user is not an expert, calculate amount of active trust given to this expert
			return getVoteDelegationPower(odUser, voters, c, n, expert);
		}
		Double w = new Double(0); // Sum the given trust in this category
		for (Expert e : experts) {
			if (e.getCategory().getId().equals(c.getId())) {
				List<Representation> representations = Representation.findRepresentationsByExpertAndTrustGreaterThan(e, BigDecimal.ZERO).getResultList();
				for (Representation r : representations) {
					w += getVoteWeigth(r.getOdUser(), voters, c, n - 1, e);
				}
			}
		}
		return w;
	}

	private Double getVoteDelegationPower(ODUser odUser, Set<ODUser> voters,
			Category c, int n, Expert expert) {
		// TODO Auto-generated method stub
		return null;
	}

	private Double getVoteDelegationPower(ODUser odUser, Set<ODUser> voters, int n) {
		// TODO Auto-generated method stub
		return null;
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
			TypedQuery<Vote> voteQuery = Vote.findVotesByOdUserAndPropositionLatest((ODUser) user, p);
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
