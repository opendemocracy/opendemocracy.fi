package fi.opendemocracy.web.mongo;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Required;

import com.mongodb.DB;
import com.mongodb.Mongo;

public class DbFactoryBean  implements FactoryBean<DB> {
	   
	private Mongo mongo;
	private String name;
	
	@Override
	public DB getObject() throws Exception {
	  
	    return mongo.getDB(name);
	}
	
	@Override
	public Class<?> getObjectType() {
	    return DB.class;
	}
	
	@Override
	public boolean isSingleton() {
	    return true;
	}
	
	@Required
	public void setMongo(Mongo mongo) {
	    this.mongo = mongo;
	}
	
	@Required   
	public void setName(String name) {
	    this.name = name;
	}
}