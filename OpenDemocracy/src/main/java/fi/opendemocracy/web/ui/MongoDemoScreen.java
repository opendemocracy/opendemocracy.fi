package fi.opendemocracy.web.ui;

import fi.opendemocracy.web.OpenDemocracyVotingApplication;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;

public class MongoDemoScreen extends Panel
{
	private static final long serialVersionUID = 1L;
	private OpenDemocracyVotingApplication app;
	private Button clear;
	private Button fill;
	private TextArea content;


	public MongoDemoScreen(OpenDemocracyVotingApplication app)
	{
		this.app = app;

		clear = new Button("Clear people collection");
		clear.addListener(new ListenerMongoClear(this.app, this));

		content = new TextArea();
		content.setSizeFull();


		fill = new Button("Fill people collection");
		fill.addListener(new ListenerMongoFill(this.app, this));

		this.addComponent(clear);
		this.addComponent(content);
		this.addComponent(fill);

		this.setSizeFull();
	}


	private void updateContent()
	{
		DBCollection coll = this.app.db.getCollection("people");
		DBCursor cur = coll.find();
		String result = "";

		while (cur.hasNext())
		{
			DBObject res = cur.next();
			
			result = result  + cur.curr().get("id") + "," + cur.curr().get("name") + "," + cur.curr().get("gender") + "\n";
		}
		
		this.content.setValue(result);
	}

	private static final class ListenerMongoFill implements ClickListener
	{
		private static final long serialVersionUID = 1L;
		private OpenDemocracyVotingApplication app;
		private MongoDemoScreen screen;


		public ListenerMongoFill(OpenDemocracyVotingApplication app, MongoDemoScreen screen)
		{
			this.app = app;
			this.screen = screen;
		}


		@Override
		public void buttonClick(ClickEvent event)
		{
			DBCollection coll = this.app.db.getCollection("people");

			coll.ensureIndex(new BasicDBObject("id", 1).append("unique", true));
			coll.createIndex(new BasicDBObject("name", 1));
			coll.insert(makePersonDocument(6655, "James", "male"));
			coll.insert(makePersonDocument(6797, "Bond", "male"));
			coll.insert(makePersonDocument(6643, "Cheryl", "female"));
			coll.insert(makePersonDocument(7200, "Scarlett", "female"));
			coll.insert(makePersonDocument(6400, "Jacks", "male"));

			this.screen.updateContent();

		}


		private BasicDBObject makePersonDocument(int id, String name, String gender)
		{
			BasicDBObject doc = new BasicDBObject();

			doc.put("id", id);
			doc.put("name", name);
			doc.put("gender", gender);

			return doc;
		}
	}

	private static final class ListenerMongoClear implements ClickListener
	{
		private static final long serialVersionUID = 1L;
		private OpenDemocracyVotingApplication app;
		private MongoDemoScreen screen;


		public ListenerMongoClear(OpenDemocracyVotingApplication app, MongoDemoScreen screen)
		{
			this.app = app;
			this.screen = screen;
		}


		@Override
		public void buttonClick(ClickEvent event)
		{
			DBCollection coll = this.app.db.getCollection("people");

			// clear records if any
			DBCursor cur = coll.find();
			while (cur.hasNext())
			{
				coll.remove(cur.next());
			}

			this.screen.updateContent();
		}

	}
}
