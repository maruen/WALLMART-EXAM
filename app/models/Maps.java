package models;

import javax.persistence.Entity;

import play.data.validation.Constraints;

import com.avaje.ebean.Model;

@Entity
public class Maps extends Model {
    
    @Constraints.Required
    public String mapName;
    
    @Constraints.Required
    public String origin;
    
    @Constraints.Required
    public String destiny;
    
    @Constraints.Required
    public String distance;
    
    
    
}
